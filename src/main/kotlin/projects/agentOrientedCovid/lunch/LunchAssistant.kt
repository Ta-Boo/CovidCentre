package projects.agentOrientedCovid.lunch

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.TriangularRNG
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.*
import projects.monteCarlo.RangedGenerator

class LunchAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent

) : Process(id, simulation, myAgent) {

    private object StartingTime {
        const val admins = 10800.0
        const val doctors = 13500.0
        const val nurses = 19800.0
    }

    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when (msg.code()) {
            IDMessage.start -> {
                val copy = msg.createCopy().msg
                copy.setCode(IDMessage.START_LUNCH)
                when (id()) {

                    IDAgent.LUNCH_ASSISTANT_ADMINS-> {
                        hold(StartingTime.admins, copy)
                    }
                    IDAgent.LUNCH_ASSISTANT_DOCTORS -> {
                        hold(StartingTime.doctors, copy)
                    }
                    IDAgent.LUNCH_ASSISTANT_NURSES -> {
                        hold(StartingTime.nurses, copy)
                    }
                }
            }

            IDMessage.START_LUNCH -> {
                assistantFinished(msg)
            }

        }
    }
}
