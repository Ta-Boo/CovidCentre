package projects.covid.consumers

import projects.monteCarlo.TriangularGenerator
import projects.covid.*
import projects.covid.events.Event
import projects.covid.events.VaccinationFinished
import projects.covid.events.VaccinationStarted

class Nurse(utility: Utility): Consumer(utility) {
    var patient: Patient? = null
    var isBussy: Boolean = false
        get() = patient != null
    val generator = TriangularGenerator(20.0, 100.0, 75.0)

    override fun consume(patient: Patient) {
        this.patient = patient
        utility.addEvents(planEvents())
    }

    override fun release(id: Int?): Patient? {
        val patient = this.patient!!
        utility.addEvents(planCallingPatient())
        this.patient = null
        return patient
    }

    override fun planEvents(): Array<Event> {
        val duration = generator.nextInt()
        workingTime += duration
        return arrayOf(VaccinationFinished(utility.actualTime + duration, utility, this, patient!!))
    }

    override fun reset() {
        patient = null
        workingTime = 0
    }

    fun planCallingPatient(): Array<Event> {
        val waiting = utility.vaccinationQueue.container.size > 0
        return if (waiting) {
            arrayOf(VaccinationStarted(utility.actualTime, utility, this))
        } else
            return arrayOf()
    }
}