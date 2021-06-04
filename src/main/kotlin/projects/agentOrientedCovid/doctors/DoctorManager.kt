package projects.agentOrientedCovid.doctors

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.msg

class DoctorManager(
        simulation: Simulation,
        agent: Agent
): Manager(IDAgent.DOCTOR_MANAGER, simulation, agent) {

    override fun processMessage(message: MessageForm) {
        val msg = message.msg
        when(msg.code()) {
            IDMessage.START_EXAMINATION -> {
                myAgent().processNewPatient(msg)
            }
            IDMessage.finish -> {
                if (myAgent().assistants.map { it.id() }.contains(msg.sender().id())) {
                    //MARK: examination finished
                    msg.setCode(IDMessage.PATIENT_EXAMINATED)
                    myAgent().callPatient(msg)

                    val copy = msg.createCopy()
                    copy.setAddressee(IDAgent.BOSS)
                    copy.setCode(IDMessage.PATIENT_EXAMINATED)
                    notice(copy)
                }
            }
            IDMessage.ASK_FOR_LUNCH -> {
                msg.setCode(myAgent().askDoctorForLunch())
                response(msg)
            }

            IDMessage.LUNCH_FINISHED -> {
                myAgent().doctorCameBackFromLunch(msg.worker)
                msg.worker = -1
            }
        }
    }

    override fun myAgent(): DoctorAgent { return _myAgent as DoctorAgent
    }
}