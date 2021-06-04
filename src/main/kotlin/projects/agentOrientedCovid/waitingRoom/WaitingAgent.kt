package projects.agentOrientedCovid.waitingRoom

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.nurses.NurseManager

class WaitingAgent(
        simulation: Simulation,
        parent: Agent
) : CovidAgent(IDAgent.WAITING_AGENT, simulation, parent) {
    val manager = WaitingManager(mySim(), this)
    val assistant = WaitingAssistant(IDAgent.WAITING_ASSISTANT,mySim(), this)
    val stats = Stat()
    var count = 0
    val persistentStats = Stat()

    init {
        addOwnMessage(IDMessage.START_WAITING)
        addOwnMessage(IDMessage.END_WAITING)
        addOwnMessage(IDMessage.finish)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        stats.clear()
        count = 0
    }

    override fun afterReplication() {
        persistentStats.addSample(stats.mean())
    }

}

abstract class CovidAgent(id: Int, simulation: Simulation, parent: Agent): Agent(id, simulation, parent) {
    abstract fun afterReplication()
}

