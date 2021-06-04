package projects.agentOrientedCovid.transports

import OSPABA.Agent
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.agentOrientedCovid.nurses.NurseManager


class TransportAgent(
        simulation: Simulation,
        parent: Agent
) : Agent(IDAgent.TRANSPORT_AGENT, simulation, parent) {
    val manager = TransportManager(mySim(), this)
    val examinationTransport = TransportAssistant(IDAgent.TRANSPORT_ASSISTANT_EXAMINATION, mySim(), this, UniformContinuousRNG(40.0, 90.0))
    val vaccinationTransport = TransportAssistant(IDAgent.TRANSPORT_ASSISTANT_VACCINATION, mySim(), this, UniformContinuousRNG(20.0, 45.0))
    val waitingRoomTransport = TransportAssistant(IDAgent.TRANSPORT_ASSISTANT_WAITING, mySim(), this, UniformContinuousRNG(45.0, 110.0))
    val injectionsRoomTransport = TransportAssistant(IDAgent.TRANSPORT_ASSISTANT_INJECTIONS, mySim(), this, UniformContinuousRNG(10.0, 18.0))
    val injectionsBackTransport = TransportAssistant(IDAgent.TRANSPORT_ASSISTANT_BACK_INJECTIONS, mySim(), this, UniformContinuousRNG(10.0, 18.0))

    init {
        addOwnMessage(IDMessage.TRANSPORT_EXAMINATION_START)
        addOwnMessage(IDMessage.TRANSPORT_VACCINATION_START)
        addOwnMessage(IDMessage.TRANSPORT_WAITING_START)
        addOwnMessage(IDMessage.TRANSPORT_EXAMINATION_FINISHED)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_START)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_FINISHED)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_BACK_START)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_BACK_FINISHED)
        addOwnMessage(IDMessage.finish)
    }
}

