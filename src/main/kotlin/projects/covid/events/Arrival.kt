package projects.covid.events

import projects.covid.ARRIVAL
import projects.covid.MAX_PATIENTS
import projects.covid.Utility

class PatientArrival(
        time: Int,
        utility: Utility
): Event(
        time,
        utility
) {
    override fun action() {
        if (utility.skipped.contains(utility.patientsArrived)) {
            utility.patientsArrived++
            return
        }
        val source = null
        val destination = utility.admins.filter { !it.isBussy }.shuffled().firstOrNull() ?: utility.registrationQueue
        val patient = passPatient(source, destination)
        patient.startedWaiting = utility.actualTime
        utility.patientsArrived++
        utility.patientArrived()
    }

    override fun planEvents(): Array<Event> {
        return if (utility.patientsArrived < MAX_PATIENTS) {
            arrayOf(PatientArrival(time + ARRIVAL, utility))
        } else  {
            arrayOf()
        }
    }
}