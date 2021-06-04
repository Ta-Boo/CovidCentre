package projects.agentOrientedCovid.admins

import OSPABA.Agent
import OSPABA.MessageForm
import OSPABA.Process
import OSPABA.Simulation
import OSPRNG.UniformContinuousRNG
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.monteCarlo.RangedGenerator

class AdminAssistant(
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
    private var tmp = 0.0

    private val generator = UniformContinuousRNG(140.0, 220.0)

    var workload = 0.0

    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when(msg.code()) {
            IDMessage.start -> {
                isWorking = true
                msg.setCode(IDMessage.PATIENT_REGISTERED)
                tmp = generator.sample()
                hold(tmp, msg)
            }

            IDMessage.PATIENT_REGISTERED -> {
                workload += tmp
                tmp = 0.0
                isWorking = false
                assistantFinished(message)
            }
        }
    }
}
