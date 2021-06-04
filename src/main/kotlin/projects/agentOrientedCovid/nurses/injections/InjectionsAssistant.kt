package projects.agentOrientedCovid.nurses.injections

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.ExponentialRNG
import OSPRNG.TriangularRNG
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.monteCarlo.RangedGenerator

class InjectionsAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent

) : Process(id, simulation, myAgent) {

    val generator = TriangularRNG(6.0, 10.0, 40.0)

    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when(msg.code()) {
            IDMessage.start -> {
                msg.setCode(IDMessage.INJECTIONS_PREPARE_FINISHED)
                val work = 20 * generator.sample()
                msg.time += work
                hold(work, msg)
            }

            IDMessage.INJECTIONS_PREPARE_FINISHED -> {
                assistantFinished(message)
            }
        }
    }
}
