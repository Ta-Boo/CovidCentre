package projects.covid.events

import projects.covid.Utility
import projects.covid.consumers.Administrator

class RegistrationStarted(
        time: Int,
        utility: Utility,
        private val administrator: Administrator
): Event(
        time,
        utility
) {
    override fun action() {
        val source = utility.registrationQueue
        val destination = administrator
        passPatient(source, destination)
//        if (utility.registrationQueue.container.isNotEmpty()) {
            utility.registrationStarted()
//        }
    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }
}


class RegistrationFinished(
        time: Int,
        utility: Utility,
        private val administrator: Administrator
): Event(
        time,
        utility
) {
    override fun action() {
        val source = administrator
        val destination = utility.doctors.filter { !it.isBussy }.shuffled().firstOrNull() ?: utility.examinationQueue
        val patient = passPatient(source, destination)
        patient.startedWaiting = utility.actualTime
        utility.registrationFinished()
    }

    override fun planEvents(): Array<Event> {
        return arrayOf()
    }
}