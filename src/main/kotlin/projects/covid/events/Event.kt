package projects.covid.events

import OSPABA.Adviser
import projects.covid.MAX_PATIENTS
import projects.covid.Patient
import projects.covid.Utility
import projects.covid.consumers.Administrator
import projects.covid.consumers.Consumer

//MARK: Base
abstract class Event(
    val time: Int,
    val utility: Utility
): Comparable<Event> {
    abstract fun action()
    fun passPatient(source: Consumer?, destination: Consumer) : Patient {
        val patient = source?.release() ?: Patient(utility.marker.uniqueIdentifier)
        destination.consume(patient)
        return patient
    }

    abstract fun planEvents(): Array<Event>

    override fun compareTo(other: Event) = when {
        time != other!!.time -> this.time - other!!.time
        else -> -1
    }
}

