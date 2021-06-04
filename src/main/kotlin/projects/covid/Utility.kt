package projects.covid

import projects.monteCarlo.MonteCarlo
import projects.monteCarlo.RangedGenerator
import projects.monteCarlo.seedGenerator
import projects.covid.consumers.*
import projects.covid.events.Event
import projects.covid.events.PatientArrival
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.max
import kotlin.math.min
import kotlin.math.nextUp
import kotlin.math.roundToInt


val MAX_TIME = 9 * 60 * 60

var ADMINS = 5
var DOCTORS = 6
var NURSES = 3
var ARRIVAL = 60
var MAX_PATIENTS = 540

//MARK: experiment 2
//var ADMINS = 23
//var DOCTORS = 28
//var NURSES = 14
//var ARRIVAL = 13
//var MAX_PATIENTS = 2500

//MARK: Minimize doctors
//var ADMINS = 23
//var DOCTORS = 21
//var NURSES = 14
//var ARRIVAL = 13
//var MAX_PATIENTS = 2500

//MARK: Validation
//var ADMINS = 6
//var DOCTORS = 8
//var NURSES = 4
//var ARRIVAL = 33
//var MAX_PATIENTS = 1000


class Utility : MonteCarlo() {
    var admins = Array(ADMINS) { Administrator(this) }
    var doctors = Array(DOCTORS) { Doctor(this) }
    var nurses = Array(NURSES) { Nurse(this) }

    val registrationQueue = WrappedQueue(this)
    val examinationQueue = WrappedQueue(this)
    val vaccinationQueue = WrappedQueue(this)
    val waitingRoom = WrappedArray(this)
    val donePatients = Ending(this)

    private var statistics = Statistics(this)
    var patientsArrived = 0

    var delay = 30L

    var events = 0
    var skipped = arrayOf<Int>()
    var marker = Identifier()
    var actualTime = 0
        private set
    var started = false
    var paused = false


    private val skipGenerator = RangedGenerator(5.0, 25.0)
    private val calendar = PriorityQueue<Event>()


    private fun generateSkipped() {
        val count = skipGenerator.nextInt()
        val result = (0..MAX_PATIENTS).shuffled(Random(seedGenerator.nextLong()))
//        skipped = result.take(count).toTypedArray()
    }

    fun fullReset() {
        statistics.reset()
        reset()
    }

    fun resetStats() {
        statistics = Statistics(this)
    }


    fun reset() {
        arrayOf(admins, nurses, doctors).forEach {
            it.forEach { consumer ->
                consumer.reset()
            }
        }

        arrayOf(
                registrationQueue,
                examinationQueue,
                vaccinationQueue,
                waitingRoom,
                donePatients).forEach {
            it.reset()
        }
        calendar.clear()
        patientsArrived = 0
        delay = 30L
        events = 0
        skipped = arrayOf()
        actualTime = 0
        started = false
        paused = false
        marker.reset()

    }


    fun addEvents(events: Array<Event>) {
        calendar.addAll(events)
        this.events += events.size
    }

    fun patientArrived() {
        statistics.registrationQueueLength.addValue(registrationQueue.container.size)
//        statistics.registrationQueueLength.addValue(registrationQueue.container.size)
    }

    fun registrationStarted() {
        statistics.registrationQueueLength.addValue(registrationQueue.container.size)
    }

    fun logRegistrationWaiting(waitingTime: Int) {
        statistics.registrationWaiting.addValue(waitingTime)
    }

    fun registrationFinished() {
        statistics.examinationQueueLength.addValue(examinationQueue.container.size)
    }

    fun examinationStarted(waitingTime: Int) {
        statistics.examinationQueueLength.addValue(examinationQueue.container.size)
        statistics.examinationWaiting.addValue(waitingTime)
    }

    fun examinationFinished() {
        statistics.vaccinationQueueLength.addValue(vaccinationQueue.container.size)
    }

    fun vaccinationStarted(waitingTime: Int) {
        statistics.vaccinationQueueLength.addValue(vaccinationQueue.container.size)
        statistics.vaccinationWaiting.addValue(waitingTime)
    }

    fun vaccinationFinished() {
        statistics.waitingRoom.addValue(waitingRoom.container.size)
    }

    fun waitingFinished() {
        statistics.waitingRoom.addValue(waitingRoom.container.size)
    }

    override fun runReplication(callback: (Int) -> Unit) {
        if (started) {
            paused = !paused
        } else {
            started = true
            var events = 0
            //            actualTime < MAX_TIME &&
            while (calendar.isNotEmpty()) {
                if (paused) {
                    Thread.sleep(500)
                    if (!started) {
                        return
                    }
                    continue
                }
                events++
                val event = calendar.remove()
                actualTime = event.time
                event.action()
                calendar.addAll(event.planEvents())
                if (singleRun) {
                    val fps = (((1 - (1.0 / 300.0 * delay)) * 60) + 1).toLong()
                    if (events % fps == 0L) {
                        callback(events)
                        Thread.sleep(delay)
                    }
                }
            }
            saveDailyStats()
            callback(-1)
            fullReset()
        }
    }

    private fun saveDailyStats() {
        statistics.saveDaily()
    }

    fun calculateDailyData(): UIData {
        return UIData(
                workload = statistics.constructDailyWorkloadData(),
                queues = statistics.constructDailyLengthData(),
                waitings = statistics.constructDailyWaitingData())
    }

    fun calculateSingleRunStatistics() = UIStats(
                workload = statistics.constructWorkloadStats(),
                queues = statistics.constructLengthStats(),
                waitings = statistics.constructWaitingStats()
        )
    fun interval() = statistics.waitingRoom


    override fun afterSimulationRun() {
        fullReset()
        paused = false
        started = false
    }

    override fun beforeSimulationRun() {
        generateSkipped()
        admins = Array(ADMINS) { Administrator(this) }
        doctors = Array(DOCTORS) { Doctor(this) }
        nurses = Array(NURSES) { Nurse(this) }
        calendar.add(PatientArrival(0, this))
    }

}