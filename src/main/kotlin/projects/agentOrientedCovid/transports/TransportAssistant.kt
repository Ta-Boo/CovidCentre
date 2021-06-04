package projects.agentOrientedCovid.transports

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.monteCarlo.RangedGenerator
class TransportAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent,
        private val generator: UniformContinuousRNG

) : Process(id, simulation, myAgent) {

    var isBusy = false

    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when(msg.code()) {
            IDMessage.start -> {
                isBusy = true
                msg.setCode(IDMessage.TRANSPORT_EXAMINATION_FINISHED)
                val time = generator.sample()
                if (id() == IDAgent.TRANSPORT_ASSISTANT_BACK_INJECTIONS || id() == IDAgent.TRANSPORT_ASSISTANT_INJECTIONS) {
                    msg.time += time
                }
                hold(time, msg)
            }

            IDMessage.TRANSPORT_EXAMINATION_FINISHED -> {
                assistantFinished(message)
            }
        }
    }
}
