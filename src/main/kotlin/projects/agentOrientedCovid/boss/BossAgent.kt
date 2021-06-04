package projects.agentOrientedCovid.boss

import OSPABA.Agent
import OSPABA.Simulation
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.Message
import projects.agentOrientedCovid.IDMessage


class BossAgent(
        simulation: Simulation
): Agent(
        IDAgent.BOSS,
        simulation,
        null) {
    val manager = BossManager(simulation, this)

    init {

        addOwnMessage(IDMessage.START)
        addOwnMessage(IDMessage.PATIENT_ARRIVED)
        addOwnMessage(IDMessage.PATIENT_REGISTERED)
        addOwnMessage(IDMessage.TRANSPORT_EXAMINATION_START)
        addOwnMessage(IDMessage.TRANSPORT_EXAMINATION_FINISHED)
        addOwnMessage(IDMessage.START_EXAMINATION)
        addOwnMessage(IDMessage.TRANSPORT_VACCINATION_START)
        addOwnMessage(IDMessage.TRANSPORT_VACCINATION_FINISHED)
        addOwnMessage(IDMessage.PATIENT_EXAMINATED)
        addOwnMessage(IDMessage.START_VACCINATION)
        addOwnMessage(IDMessage.PATIENT_VACCINATED)
        addOwnMessage(IDMessage.TRANSPORT_WAITING_START)
        addOwnMessage(IDMessage.TRANSPORT_WAITING_FINISHED)
        addOwnMessage(IDMessage.START_WAITING)
        addOwnMessage(IDMessage.WAITING_FINISHED)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_FINISHED)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_BACK_FINISHED)
        addOwnMessage(IDMessage.INJECTIONS_PREPARE_FINISHED)
        addOwnMessage(IDMessage.STOP)
    }

    fun runReplication() {
        val msg = Message(_mySim)
        msg.setAddressee(this)
        msg.setCode(IDMessage.START)
        manager().notice(msg)
    }

    override fun prepareReplication() {
        super.prepareReplication()
        val manager = manager() as BossManager
        manager.counter = 0
    }
}
