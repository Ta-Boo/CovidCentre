package projects.covid.consumers

import projects.covid.events.Event
import projects.covid.Patient
import projects.covid.Utility

class Ending(utility: Utility): Consumer(utility) {
    var patients = arrayListOf<Patient>()

    override fun consume(patient: Patient) {
        patients.add(patient)
    }

    override fun release(id: Int?): Patient? {
        return null
    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }

    fun planCallingPatient(): Array<Event> {
        return arrayOf()
    }

    override fun reset() {
        patients.clear()
    }
}