package projects.covid.events

import projects.covid.Utility
import projects.covid.consumers.Doctor

class ExaminationStarted(
        time: Int,
        utility: Utility,
        private val doctor: Doctor
): Event(
        time,
        utility
) {
    override fun action() {
        val source = utility.examinationQueue
        val destination = doctor
        val patient = passPatient(source, destination)
        utility.examinationStarted(utility.actualTime - patient.startedWaiting!!)
    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }
}


class ExaminationFinished(
        time: Int,
        utility: Utility,
        private val doctor: Doctor
): Event(
        time,
        utility
) {
    override fun action() {
        val source = doctor
        val destination = utility.nurses.filter { !it.isBussy }.shuffled().firstOrNull() ?: utility.vaccinationQueue
        val patient = passPatient(source, destination)
        patient.startedWaiting = utility.actualTime
        utility.examinationFinished()
    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }
}