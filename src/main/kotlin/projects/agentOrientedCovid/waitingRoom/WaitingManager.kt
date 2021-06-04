package projects.agentOrientedCovid.waitingRoom

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg


class WaitingManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.WAITING_MANAGER, simulation, agent) {
    var debug = 0

    override fun processMessage(message: MessageForm) {
        val msg = message.msg

        when(msg.code()) {

            IDMessage.START_WAITING -> {
                msg.setAddressee(myAgent().assistant)
                startContinualAssistant(msg)
            }

            IDMessage.finish -> {
                debug++
                //MARK: registration finished
                    msg.setCode(IDMessage.WAITING_FINISHED)
                    msg.setAddressee(IDAgent.BOSS)
                    notice(msg)
            }
        }
    }

    override fun myAgent(): WaitingAgent { return _myAgent as WaitingAgent }
}