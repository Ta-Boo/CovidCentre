package projects.agentOrientedCovid.nurses.injections

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message

class InjectionsAgent(
        simulation: Simulation,
        parent: Agent
) : Agent(IDAgent.INJECTIONS_AGENT, simulation, parent) {
    val manager = InjectionsManager(_mySim, this)
    val assistant = InjectionsAssistant(IDAgent.INJECTIONS_ASSISTANT, simulation, this)

    val queue = SimQueue<Int>(WStat(simulation)) //MARK: Queue for registration
    var presentNurses = 0


    val persistentQueueData = Stat()

    init {
        addOwnMessage(IDMessage.INJECTIONS_PREPARE)
        addOwnMessage(IDMessage.INJECTIONS_PREPARE_FINISHED)
    }

    fun processNurse(msg: Message) {
        if (presentNurses > 2) { error("Forbiden state") }
        if (presentNurses == 2) {
            queue.enqueue(msg.worker)
            msg.worker = -1
        } else {
            presentNurses++
            msg.setAddressee(IDAgent.INJECTIONS_ASSISTANT)
            manager().startContinualAssistant(msg)
        }
    }

    fun releaseNurse(msg: Message) {
        callNurseIfWaiting(msg)
        presentNurses--
    }

    private fun callNurseIfWaiting(msg: Message) {
        if (queue.isNotEmpty()) {
            val nurseID = queue.dequeue()
            msg.worker = nurseID
            msg.setCode(IDMessage.INJECTIONS_PREPARE)
            msg.setAddressee(this)
            manager.notice(msg)
        }
    }


    override fun prepareReplication() {
        super.prepareReplication()
        persistentQueueData.addSample(queue.lengthStatistic().mean())
        queue.clear()
        presentNurses = 0
    }
}
