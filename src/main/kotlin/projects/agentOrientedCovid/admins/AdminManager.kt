package projects.agentOrientedCovid.admins

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg

//class AdminManager(
//        simulation: Simulation,
//        agent: Agent
//): Manager(IDAgent.ADMIN_MANAGER, simulation, agent) {
//    override fun processMessage(p0: MessageForm?) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//}

class AdminManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.ADMIN_MANAGER, simulation, agent) {
    var debug = 0


    override fun processMessage(message: MessageForm) {
        val msg = message.msg

        when(msg.code()) {

            IDMessage.START_REGISTRATION -> {
                myAgent().processNewPatient(msg)
            }

            IDMessage.finish -> {
                    //MARK: registration finished

                    msg.setCode(IDMessage.PATIENT_REGISTERED)
                    myAgent().callPatient(msg)

                    val copy = msg.createCopy()
                    copy.setAddressee(IDAgent.BOSS)
                    copy.setCode(IDMessage.PATIENT_REGISTERED)
                    notice(copy)
            }

            IDMessage.ASK_FOR_LUNCH -> {
                msg.setCode(myAgent().askAssistentForLunch())
                response(msg)
            }

            IDMessage.LUNCH_FINISHED -> {
                myAgent().assistentCameBackFromLunch(msg.worker)
                msg.worker = -1
            }

        }
    }

    override fun myAgent(): AdminAgent { return _myAgent as AdminAgent }
}