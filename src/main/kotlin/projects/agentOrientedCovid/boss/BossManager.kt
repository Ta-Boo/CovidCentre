package projects.agentOrientedCovid.boss

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Workers
import projects.agentOrientedCovid.msg


class BossManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.BOSS_MANAGER, simulation, agent) {

    var counter = 0
    override fun processMessage(message: MessageForm) {
        val msg = message.msg
        when (message.code()) {

            IDMessage.START -> {
                msg.setAddressee(IDAgent.SURROUNDING)
                notice(message)
                val copy = msg.createCopy()
                copy.setAddressee(IDAgent.LUNCH_AGENT)
                copy.setCode(IDMessage.START)
                notice(copy)
            }

            IDMessage.PATIENT_ARRIVED -> {
                msg.setCode(IDMessage.START_REGISTRATION)
                msg.setAddressee(IDAgent.ADMIN_AGENT)
                notice(msg)
            }

            IDMessage.PATIENT_REGISTERED -> {
                msg.setCode(IDMessage.TRANSPORT_EXAMINATION_START)
                msg.setAddressee(IDAgent.TRANSPORT_AGENT)
                notice(msg)
            }

            IDMessage.TRANSPORT_EXAMINATION_FINISHED -> {
                msg.setCode(IDMessage.START_EXAMINATION)
                msg.setAddressee(IDAgent.DOCTOR_AGENT)
                notice(msg)
             }

            IDMessage.PATIENT_EXAMINATED -> {
                msg.setCode(IDMessage.TRANSPORT_VACCINATION_START)
                msg.setAddressee(IDAgent.TRANSPORT_AGENT)
                notice(msg)
            }

            IDMessage.TRANSPORT_VACCINATION_FINISHED -> {
                msg.setCode(IDMessage.START_VACCINATION)
                msg.setAddressee(IDAgent.NURSE_AGENT)
                notice(msg)
            }

            IDMessage.PATIENT_VACCINATED -> {
                msg.setCode(IDMessage.TRANSPORT_WAITING_START)
                msg.setAddressee(IDAgent.TRANSPORT_AGENT)
                notice(msg)
            }

            IDMessage.TRANSPORT_WAITING_FINISHED -> {
                msg.setCode(IDMessage.START_WAITING)
                msg.setAddressee(IDAgent.WAITING_AGENT)
                notice(msg)
            }

            IDMessage.TRANSPORT_INJECTION_FINISHED -> {
                msg.setCode(IDMessage.INJECTIONS_PREPARE)
                msg.setAddressee(IDAgent.INJECTIONS_AGENT)
                notice(msg)
            }

            IDMessage.INJECTIONS_PREPARE_FINISHED -> {
                msg.setCode(IDMessage.TRANSPORT_INJECTION_BACK_START)
                msg.setAddressee(IDAgent.TRANSPORT_AGENT)
                notice(msg)
            }

            IDMessage.TRANSPORT_INJECTION_BACK_FINISHED -> {
                msg.setCode(IDMessage.INJECTIONS_PREPARED)
                msg.setAddressee(IDAgent.NURSE_AGENT)
                notice(msg)
            }

            IDMessage.WAITING_FINISHED -> {
                counter++
                if (counter == Workers.PATIENTS) {
                    mySim().stopReplication(mySim().currentTime())
                }
            }
        }
    }

}
