package projects.agentOrientedCovid.nurses

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg


class NurseManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.NURSE_MANAGER, simulation, agent) {

    override fun processMessage(message: MessageForm) {
        val msg = message.msg

        when(msg.code()) {

            IDMessage.START_VACCINATION -> {
                myAgent().processNewPatient(msg)
            }

            IDMessage.finish -> {
                val sender = myAgent().assistants.first { it.id() == message.sender().id() }
//                if (sender.injections > 0) {
                    msg.setCode(IDMessage.PATIENT_VACCINATED)
                    myAgent().callPatient(msg)
//                } else {
////                    error("no more injections")
//                }


                val copy = msg.createCopy()
                copy.setAddressee(IDAgent.BOSS)
                copy.setCode(IDMessage.PATIENT_VACCINATED)
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

            IDMessage.INJECTIONS_PREPARED -> {
                myAgent().injectionsPrepared(msg.worker, msg.time)
                msg.time = 0.0
                msg.worker = -1
            }
        }
    }

    override fun myAgent(): NurseAgent { return _myAgent as NurseAgent }
}