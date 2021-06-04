package projects.covid.consumers

import projects.monteCarlo.ExponentialGenerator
import projects.monteCarlo.RangedGenerator
import projects.covid.*
import projects.covid.events.Event
import projects.covid.events.ExaminationFinished
import projects.covid.events.ExaminationStarted
import projects.monteCarlo.TriangularGenerator


class Doctor(utility: Utility): Consumer(utility) {
    var patient: Patient? = null
    var isBussy: Boolean = false
        get() = patient != null
    val generator = ExponentialGenerator(1/260.0)
    val waitingGenerator = RangedGenerator(0.0, 100.0)

    override fun consume(patient: Patient) {
        this.patient = patient
        patient.waitingTime = if(waitingGenerator.nextDouble() <= 95) 15*60 else 30*60
        utility.addEvents(planEvents())
    }

    override fun release(id: Int?): Patient? {
        val patient = this.patient!!
        this.patient = null
        utility.addEvents(planCallingPatient())
        return patient
    }

    override fun reset() {
        patient = null
        workingTime = 0
    }

    override fun planEvents(): Array<Event> {
        val duration = generator.nextInt()
        workingTime += duration
        return arrayOf(ExaminationFinished(utility.actualTime + duration, utility, this))
    }

    fun planCallingPatient(): Array<Event> {
        val waiting = utility.examinationQueue.container.size > 0

        return if (waiting) {
            arrayOf(ExaminationStarted(utility.actualTime, utility, this))
        } else
            arrayOf()
    }
}