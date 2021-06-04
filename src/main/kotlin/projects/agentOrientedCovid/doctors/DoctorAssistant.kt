package projects.agentOrientedCovid.doctors

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.ExponentialRNG
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.monteCarlo.RangedGenerator

class DoctorAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent

) : Process(id, simulation, myAgent) {

    var lunchFinished = false
    var askedForLunch = false
    var atLunch = false
    var isWorking = false
    var isAway = false
    fun isBusy() = isAway || isWorking || atLunch

    val generator = ExponentialRNG(260.0)
    var workload = 0.0

    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when(msg.code()) {
            IDMessage.start -> {
                isWorking = true
                msg.setCode(IDMessage.PATIENT_EXAMINATED)
                val work = generator.sample()
                workload += work
                hold(work, msg)
            }

            IDMessage.PATIENT_EXAMINATED -> {
                isWorking = false
                assistantFinished(message)
            }
        }
    }
}
