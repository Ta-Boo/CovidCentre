package projects.agentOrientedCovid.nurses

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
class NurseAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent

) : Process(id, simulation, myAgent) {

    private val generator = TriangularRNG(20.0, 75.0, 100.0)

    var injections = 20

    var lunchFinished = false
    var askedForLunch = false
    var atLunch = false
    var isWorking = false
    var isAway = false
    fun isBusy() = isAway || isWorking || atLunch
    var workload = 0.0

    override fun processMessage(message: MessageForm) {
        val msg = message as Message

        when(msg.code()) {
            IDMessage.start -> {
                isWorking = true
                msg.setCode(IDMessage.PATIENT_VACCINATED)
                val time = generator.sample()
                workload += time
                hold(time, msg)
            }

            IDMessage.PATIENT_VACCINATED -> {
                injections--
                if (injections == 0) {
                    isWorking = false
                    isAway = true //TODO: this is the only factor needed
                    val cpy = msg.createCopy()
                    msg.setCode(IDMessage.TRANSPORT_INJECTION_START)
                    msg.worker = id()
                    msg.setAddressee(IDAgent.TRANSPORT_AGENT)
                    myAgent().manager().notice(msg)
                    assistantFinished(cpy)

                } else {
                    isWorking = false
                    assistantFinished(message)
                }
            }

        }
    }
}
