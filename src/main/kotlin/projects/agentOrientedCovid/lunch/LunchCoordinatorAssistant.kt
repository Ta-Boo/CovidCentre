package projects.agentOrientedCovid.lunch

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.*
import projects.monteCarlo.RangedGenerator

class LunchCoordinatorAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent

) : Process(id, simulation, myAgent) {

    private val pathGenerator = UniformContinuousRNG(70.0, 200.0)
    private val lunchGenerator = TriangularRNG(300.0, 900.0, 1800.0)


    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when (msg.code()) {
            IDMessage.start -> {
                val copy = msg.createCopy()
                copy.setCode(IDMessage.LUNCH_FINISHED)
                val time = 2 * pathGenerator.sample() + lunchGenerator.sample()
                hold(time, copy)
            }

            IDMessage.LUNCH_FINISHED -> {
                assistantFinished(msg)
            }
        }
    }
}
