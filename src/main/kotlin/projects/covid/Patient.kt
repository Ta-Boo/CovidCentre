package projects.covid

class Patient(
        val id: Int
) {
    var waitingTime: Int? = null
    var lastQueue: LastQueue? = null
    var startedWaiting: Int = -90
    var stoppeddWaiting: Int = -90
}

enum class LastQueue {
    REGISTRATION, EXAMINATION, VACCINATION
}

class Identifier {
    var uniqueIdentifier =  1
        get() {
            val result = field
            field += 1
            return result
        }
    private set

    fun reset() {
        uniqueIdentifier = 1
    }
}