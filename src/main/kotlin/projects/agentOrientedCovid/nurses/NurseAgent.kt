package projects.agentOrientedCovid.nurses

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.agentOrientedCovid.Workers
import projects.agentOrientedCovid.waitingRoom.CovidAgent


class NurseAgent(
        simulation: Simulation,
        parent: Agent
) : CovidAgent(IDAgent.NURSE_AGENT, simulation, parent) {
    val manager = NurseManager(_mySim, this)
    val assistants = Array(Workers.NURSES) { NurseAssistant(IDAgent.NURSE_ASSISTANTS[it], _mySim, this) }//MARK: Asistents
    val queue = SimQueue<Double>(WStat(simulation)) //MARK: Queue for registration
    val waitingStats = Stat()

    val persistentQueueData = Stat()
    val persistentWaitingData = Stat()
    val persistentWorkload = Stat()

    init {
        addOwnMessage(IDMessage.START_VACCINATION)
        addOwnMessage(IDMessage.PATIENT_VACCINATED)
        addOwnMessage(IDMessage.PATIENT_VACCINATED)
        addOwnMessage(IDMessage.TRANSPORT_INJECTION_FINISHED)
        addOwnMessage(IDMessage.ASK_FOR_LUNCH)
        addOwnMessage(IDMessage.LUNCH_FINISHED)
        addOwnMessage(IDMessage.INJECTIONS_PREPARED)
        addOwnMessage(IDMessage.finish)
    }


    fun processNewPatient(msg: Message) {
        val freeNurses = assistants.filter { !it.isBusy() }
        if (freeNurses.isNotEmpty()) {
            val chosen = freeNurses.shuffled().first()
            if (chosen.askedForLunch) {
                queue.enqueue(mySim().currentTime())
                msg.setAddressee(IDAgent.LUNCH_AGENT)
                msg.worker = chosen.id()
                msg.setCode(IDMessage.START_LUNCH)
                sendForLunch(chosen.id())
                manager.notice(msg)
            } else {
                msg.setAddressee(chosen)
                manager.startContinualAssistant(msg)
                waitingStats.addSample(.0)
            }

        } else {
            queue.enqueue(mySim().currentTime())
        }
    }

    fun askAssistentForLunch(): Int {
        val atLunch = assistants.filter { it.askedForLunch }.count()
        if (atLunch > assistants.count()/2 ) {
            return IDMessage.FAILED
        }
        assistants.firstOrNull { !it.askedForLunch && !it.lunchFinished }?.let {
            it.askedForLunch = true
            return IDMessage.SUCCESS
        }
        return IDMessage.FAILED
    }

    fun sendForLunch(id: Int) {
        assistants.first { it.id() == id }.apply {
            atLunch = true
        }
    }

    fun assistentCameBackFromLunch(id: Int) {
        if (!assistants.map { it.id() }.contains(id)) { error("Must be one of the assistants") }
        assistants.first { it.id() == id }.apply {
            askedForLunch = false
            lunchFinished = true
            atLunch = false
        }
        askAssistentForLunch()
    }

    fun callPatient(msg: Message)  {
        if (queue.isNotEmpty()) {
            val waiting = mySim().currentTime() - queue.dequeue()
            waitingStats.addSample(waiting)
            msg.setCode(IDMessage.START_VACCINATION)
            msg.setAddressee(this)
            manager.notice(msg)
        }
    }

    fun actualWorkload()  = assistants.map { it.workload/mySim().currentTime() }.average()

    fun injectionsPrepared(id: Int, time: Double){
        if (time <= 1.0) { error("should be larger")}
        val assistant = assistants.first { it.id() == id }
        assistant.workload += time
        assistant.isAway = false
        assistant.isWorking = false
        assistant.injections = 20
    }

    override fun prepareReplication() {
        super.prepareReplication()
        queue.clear()
        waitingStats.clear()
        assistants.forEach {
            it.workload = 0.0
            it.lunchFinished = false
            it.askedForLunch = false
            it.atLunch = false
            it.isWorking = false
            it.isAway = false
            it.injections = 20
        }
    }

    override fun afterReplication() {
        persistentQueueData.addSample(queue.lengthStatistic().mean())
        persistentWaitingData.addSample(waitingStats.mean())
        persistentWorkload.addSample(actualWorkload())
    }


}

