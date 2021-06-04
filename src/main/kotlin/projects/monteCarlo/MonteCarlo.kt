package projects.monteCarlo
import kotlin.concurrent.thread


abstract class MonteCarlo(){
    abstract fun runReplication(callback: (Int) -> Unit)
    abstract fun afterSimulationRun()
    abstract fun beforeSimulationRun()

    var replication : Int = 1
    var singleRun = true


    fun runReplications(repetitions: Int, replicationCallback: (Int) -> Unit) {
        singleRun = false
        while (replication <= repetitions) {
            beforeSimulationRun()
            runReplication{}
            replicationCallback(replication)
            afterSimulationRun()
            replication++
        }
        singleRun = true
    }
}
