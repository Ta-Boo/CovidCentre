package projects.agentOrientedCovid.nurses.injections

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg
import projects.agentOrientedCovid.nurses.injections.InjectionsAgent

class InjectionsManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.INJECTIONS_MANAGER, simulation, agent) {

    override fun processMessage(message: MessageForm) {
        val msg = message.msg
        when(msg.code()) {
            IDMessage.INJECTIONS_PREPARE -> {
                myAgent().processNurse(msg)
            }

            IDMessage.finish -> {
                val cpy = msg.createCopy().msg
                myAgent().releaseNurse(cpy)


                msg.setCode(IDMessage.INJECTIONS_PREPARE_FINISHED)
                msg.setAddressee(IDAgent.BOSS)
                notice(msg)
            }
        }
    }

    override fun myAgent(): InjectionsAgent { return _myAgent as InjectionsAgent }
}