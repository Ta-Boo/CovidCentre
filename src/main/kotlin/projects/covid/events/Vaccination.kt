package projects.covid.events

import projects.covid.Patient
import projects.covid.Utility
import projects.covid.consumers.Nurse

class VaccinationStarted(
        time: Int,
        utility: Utility,
        private val nurse: Nurse
): Event(
        time,
        utility
) {
    override fun action() {
        val source = utility.vaccinationQueue
        val destination = nurse
        val patient = passPatient(source, destination)
        utility.vaccinationStarted(utility.actualTime - patient.startedWaiting!!)

    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }
}


class VaccinationFinished(
        time: Int,
        utility: Utility,
        private val nurse: Nurse,
        private val patient: Patient
): Event(
        time,
        utility
) {
    override fun action() {
        utility.vaccinationFinished()
    }

    override fun planEvents(): Array<Event> {
        return arrayOf(MonitoringStarted(utility.actualTime, utility, patient, nurse))
    }
}

