package projects.covid.consumers

import projects.covid.Patient
import projects.covid.Utility
import java.util.*
import kotlin.collections.ArrayList

class WrappedQueue(
        utility: Utility,
        val container: Queue<Patient> = LinkedList<Patient>()
): Consumer(utility) {
    override fun consume(patient: Patient) {
        container.add(patient)
    }

    override fun release(id: Int?): Patient? {
        return if (container.isNotEmpty())
            container.remove()
        else
            null
    }

    override fun reset() {
        container.clear()
    }
}


class WrappedArray(
        utility: Utility,
        val container: MutableList<Patient> = ArrayList()
): Consumer(utility) {
    override fun consume(patient: Patient) {
        container.add(patient)
    }

    override fun release(id: Int?): Patient? {
        val patientIndex = container.indexOfFirst { it.id == id }
        return container.removeAt(patientIndex)
    }

    override fun reset() {
        container.clear()
    }
}

