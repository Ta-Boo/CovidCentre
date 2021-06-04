package projects.agentOrientedCovid.lunch

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg
import projects.agentOrientedCovid.nurses.NurseAgent


class LunchManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.LUNCH_MANAGER, simulation, agent) {

    override fun processMessage(message: MessageForm) {
        val msg = message.msg
        when(msg.code()) {
            IDMessage.START -> {
                val adminMsg = msg.createCopy().msg
                adminMsg.setAddressee(myAgent().adminAssistant)
                adminMsg.worker = IDAgent.LUNCH_ASSISTANT_ADMINS
                startContinualAssistant(adminMsg)

                val doctorMsg = msg.createCopy().msg
                doctorMsg.setAddressee(myAgent().doctorsAssistant)
                doctorMsg.worker = IDAgent.LUNCH_ASSISTANT_DOCTORS
                startContinualAssistant(doctorMsg)

                val nurseMsg = msg.createCopy().msg
                nurseMsg.setAddressee(myAgent().nurseAssistant)
                nurseMsg.worker = IDAgent.LUNCH_ASSISTANT_NURSES
                startContinualAssistant(nurseMsg)
            }

            IDMessage.finish -> {
                when (msg.sender().id()) {
                    IDAgent.LUNCH_ASSISTANT_ADMINS -> {
                        msg.setCode(IDMessage.ASK_FOR_LUNCH)
                        msg.setAddressee(IDAgent.ADMIN_AGENT)
                        request(message)
                    }
                    IDAgent.LUNCH_ASSISTANT_DOCTORS -> {
                        msg.setCode(IDMessage.ASK_FOR_LUNCH)
                        msg.setAddressee(IDAgent.DOCTOR_AGENT)
                        request(message)
                    }
                    IDAgent.LUNCH_ASSISTANT_NURSES -> {
                        msg.setCode(IDMessage.ASK_FOR_LUNCH)
                        msg.setAddressee(IDAgent.NURSE_AGENT)
                        request(message)
                    }

                    IDAgent.LUNCH_ASSISTANT_COORDINATOR_ADMINS -> {
                        msg.setAddressee(IDAgent.ADMIN_AGENT)
                        msg.setCode(IDMessage.LUNCH_FINISHED)
                        notice(msg)
                    }
                    IDAgent.LUNCH_ASSISTANT_COORDINATOR_DOCTORS -> {
                        msg.setAddressee(IDAgent.DOCTOR_AGENT)
                        msg.setCode(IDMessage.LUNCH_FINISHED)
                        notice(msg)
                    }
                    IDAgent.LUNCH_ASSISTANT_COORDINATOR_NURSES -> {
                        msg.setAddressee(IDAgent.NURSE_AGENT)
                        msg.setCode(IDMessage.LUNCH_FINISHED)
                        notice(msg)
                    }
                    else -> error("Sender not supported")
                }
            }

            IDMessage.START_LUNCH -> {
                when (msg.sender().id()) {
                    IDAgent.ADMIN_AGENT ->{
                        msg.setAddressee(IDAgent.LUNCH_ASSISTANT_COORDINATOR_ADMINS)
                        startContinualAssistant(msg)
                    }

                    IDAgent.DOCTOR_AGENT ->{
                        msg.setAddressee(IDAgent.LUNCH_ASSISTANT_COORDINATOR_DOCTORS)
                        startContinualAssistant(msg)
                    }

                    IDAgent.NURSE_AGENT ->{
                        msg.setAddressee(IDAgent.LUNCH_ASSISTANT_COORDINATOR_NURSES)
                        startContinualAssistant(msg)
                    }
                }
            }

            IDMessage.SUCCESS -> {
                when (msg.sender().id()) {
                    IDAgent.ADMIN_AGENT ->{
                        msg.setCode(IDMessage.ASK_FOR_LUNCH)
                        msg.setAddressee(IDAgent.ADMIN_AGENT)
                        request(message)
                    }

                    IDAgent.DOCTOR_AGENT ->{
                        msg.setCode(IDMessage.ASK_FOR_LUNCH)
                        msg.setAddressee(IDAgent.DOCTOR_AGENT)
                        request(message)
                    }

                    IDAgent.NURSE_AGENT ->{
                        msg.setCode(IDMessage.ASK_FOR_LUNCH)
                        msg.setAddressee(IDAgent.NURSE_AGENT)
                        request(message)
                    }
                }
            }

        }
    }

    override fun myAgent(): LunchAgent { return _myAgent as LunchAgent }
}