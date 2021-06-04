package projects.covid.events


import projects.covid.Patient
import projects.covid.Utility
import projects.covid.consumers.Administrator
import projects.covid.consumers.Nurse

class MonitoringStarted(
        time: Int,
        utility: Utility,
        private val patient: Patient,
        private val nurse: Nurse
): Event(
        time,
        utility
) {
    override fun action() {
        val source = nurse
        val destination = utility.waitingRoom
        passPatient(source, destination)
    }

    override fun planEvents(): Array<Event> {
        return arrayOf(MonitoringFinished( utility.actualTime + patient.waitingTime!!, utility, patient))
    }
}


class MonitoringFinished(
        time: Int,
        utility: Utility,
        private val patient: Patient
): Event(
        time,
        utility
) {
    override fun action() {
        val source = utility.waitingRoom
        val destination = utility.donePatients
        val patientFromSource = source.release(patient.id)
        utility.waitingFinished()
        destination.consume(patientFromSource!!)
    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }
}