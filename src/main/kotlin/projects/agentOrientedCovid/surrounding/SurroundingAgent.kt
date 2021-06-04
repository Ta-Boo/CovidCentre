package projects.agentOrientedCovid.surrounding

import OSPABA.Agent
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Workers

class SurroundingAgent(
        simulation: Simulation,
        parent: Agent
): Agent(IDAgent.SURROUNDING, simulation, parent) {
    val manager = SurroundingManager(simulation, this)
    val assistant = SurroundingAssistant(simulation, this)

    init {
        addOwnMessage(IDMessage.START)
        addOwnMessage(IDMessage.PATIENT_ARRIVED)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        assistant.count = Workers.PATIENTS
    }
}
