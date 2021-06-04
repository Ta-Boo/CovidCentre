package projects.agentOrientedCovid.waitingRoom

import OSPABA.*
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.agentOrientedCovid.waitingRoom.WaitingAgent
import java.util.*

class WaitingAssistant(
        id: Int,
        simulation: Simulation,
        myAgent: Agent

) : Process(id, simulation, myAgent) {

    val generator = Random()

    override fun processMessage(message: MessageForm) {
        val msg = message as Message
        when(msg.code()) {
            IDMessage.start -> {
                msg.setCode(IDMessage.END_WAITING)
                val waitingTime = if (generator.nextDouble() <= .95) 15.0 * 60.0 else 30.0 * 60.0
                myAgent().stats.addSample(waitingTime)
                myAgent().count++
                hold(waitingTime, msg)
            }

            IDMessage.END_WAITING -> {
                myAgent().count--
                assistantFinished(message)
            }
        }
    }

    override fun myAgent(): WaitingAgent { return _myAgent as WaitingAgent }

}
