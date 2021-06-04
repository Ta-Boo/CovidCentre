package projects.agentOrientedCovid.transports

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg
import projects.agentOrientedCovid.nurses.NurseAgent


class TransportManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.TRANSPORT_MANAGER, simulation, agent) {

    override fun processMessage(message: MessageForm) {
        val msg = message.msg

        when(msg.code()) {

            IDMessage.TRANSPORT_EXAMINATION_START -> {
                msg.setAddressee(myAgent().examinationTransport)
                startContinualAssistant(msg)
            }
            IDMessage.TRANSPORT_VACCINATION_START -> {
                msg.setAddressee(myAgent().vaccinationTransport)
                startContinualAssistant(msg)
            }
            IDMessage.TRANSPORT_WAITING_START -> {
                msg.setAddressee(myAgent().waitingRoomTransport)
                startContinualAssistant(msg)
            }
            IDMessage.TRANSPORT_INJECTION_START -> {
                msg.setAddressee(myAgent().injectionsRoomTransport)
                startContinualAssistant(msg)
            }
            IDMessage.TRANSPORT_INJECTION_BACK_START -> {
                msg.setAddressee(myAgent().injectionsBackTransport)
                startContinualAssistant(msg)
            }

            IDMessage.finish -> {
                msg.setAddressee(IDAgent.BOSS)

                when (msg.sender().id()) {
                    IDAgent.TRANSPORT_ASSISTANT_EXAMINATION -> {
                        msg.setCode(IDMessage.TRANSPORT_EXAMINATION_FINISHED)
                    }
                    IDAgent.TRANSPORT_ASSISTANT_VACCINATION -> {
                        msg.setCode(IDMessage.TRANSPORT_VACCINATION_FINISHED)
                    }
                    IDAgent.TRANSPORT_ASSISTANT_WAITING -> {
                        msg.setCode(IDMessage.TRANSPORT_WAITING_FINISHED)
                    }
                    IDAgent.TRANSPORT_ASSISTANT_INJECTIONS -> {
                        msg.setCode(IDMessage.TRANSPORT_INJECTION_FINISHED)
                    }
                    IDAgent.TRANSPORT_ASSISTANT_BACK_INJECTIONS -> {
                        msg.setCode(IDMessage.TRANSPORT_INJECTION_BACK_FINISHED)
                    }
                }
                notice(msg)
            }
        }
    }

    override fun myAgent(): TransportAgent { return _myAgent as TransportAgent
    }
}