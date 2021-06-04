package projects.agentOrientedCovid.surrounding

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.CovidSimulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage

@Suppress("DUPLICATE_LABEL_IN_WHEN")
class SurroundingManager(
        simulation: Simulation,
        agent: SurroundingAgent
): Manager(
        IDAgent.SURROUNDING_MANAGER, simulation, agent){
    override fun processMessage(message: MessageForm) {

        when (message.code()) {
            IDMessage.START -> {
                message.setAddressee(_myAgent.continualAssistants().first())
                startContinualAssistant(message)
            }
            IDMessage.finish -> {
                message.setAddressee(IDAgent.BOSS)
                message.setCode(IDMessage.PATIENT_ARRIVED)
                notice(message)
            }

        }
    }


    override fun myAgent(): SurroundingAgent {
       return  _myAgent as SurroundingAgent
    }
}