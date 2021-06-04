package projects.covid.consumers

import projects.monteCarlo.RangedGenerator
import projects.covid.*
import projects.covid.events.Event
import projects.covid.events.RegistrationFinished
import projects.covid.events.RegistrationStarted

class Administrator(utility: Utility): Consumer(utility) {
    var patient: Patient? = null
    var isBussy: Boolean = false
        get() = patient != null
    val generator = RangedGenerator(140.0, 220.0)

    override fun consume(patient: Patient) {
        this.patient = patient
        patient.stoppeddWaiting = utility.actualTime
        utility.addEvents(planEvents())
    }

    override fun release(id: Int?): Patient? {
        val patient = this.patient!!
        utility.addEvents(planCallingPatient())
        this.patient = null
        val waitingTime = patient.stoppeddWaiting - patient.startedWaiting
        utility.logRegistrationWaiting(waitingTime)
        return patient
    }

    override fun planEvents(): Array<Event> {
        val duration = generator.nextInt()
        workingTime += duration
        return arrayOf(RegistrationFinished(utility.actualTime + duration, utility, this))
    }

    fun planCallingPatient(): Array<Event> {
        val waiting = utility.registrationQueue.container.size > 0
        return if (waiting) {
            arrayOf(RegistrationStarted(utility.actualTime, utility, this))
        } else
            arrayOf()
    }

    override fun reset() {
        patient = null
        workingTime = 0
    }
}