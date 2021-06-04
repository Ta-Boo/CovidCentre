package projects.covid.consumers

import projects.covid.events.Event
import projects.covid.Patient
import projects.covid.Utility

abstract class Consumer(
    val utility: Utility
) {
    var workingTime = 0

    abstract fun consume(patient: Patient)
    abstract fun release(id: Int? = null): Patient?
    abstract fun reset()
    open fun planEvents(): Array<Event> = arrayOf()

    fun calculateWorkload(): Float {
        return workingTime.toFloat() / utility.actualTime.toFloat()
    }
}
