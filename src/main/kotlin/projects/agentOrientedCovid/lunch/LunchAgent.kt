package projects.agentOrientedCovid.lunch

import OSPABA.Agent
import OSPABA.Simulation
import OSPDataStruct.SimQueue
import OSPStat.Stat
import OSPStat.WStat
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Message
import projects.agentOrientedCovid.Workers
import projects.agentOrientedCovid.nurses.NurseAssistant
import projects.agentOrientedCovid.nurses.NurseManager


class LunchAgent(
        simulation: Simulation,
        parent: Agent
) : Agent(IDAgent.LUNCH_AGENT, simulation, parent) {
    val manager = LunchManager(_mySim, this)
    val adminAssistant = LunchAssistant(IDAgent.LUNCH_ASSISTANT_ADMINS, simulation, this)
    val doctorsAssistant = LunchAssistant(IDAgent.LUNCH_ASSISTANT_DOCTORS, simulation, this)
    val nurseAssistant = LunchAssistant(IDAgent.LUNCH_ASSISTANT_NURSES, simulation, this)

    val adminCoordinatorAssistant = LunchCoordinatorAssistant(IDAgent.LUNCH_ASSISTANT_COORDINATOR_ADMINS, simulation, this)
    val doctorsCoordinatorAssistant = LunchCoordinatorAssistant(IDAgent.LUNCH_ASSISTANT_COORDINATOR_DOCTORS, simulation, this)
    val nurseCoordinatorAssistant = LunchCoordinatorAssistant(IDAgent.LUNCH_ASSISTANT_COORDINATOR_NURSES, simulation, this)

    val waitingStats = Stat()

    val persistentQueueData = Stat()
    val persistentWaitingData = Stat()
    val persistentWorkload = Stat()

    init {
        addOwnMessage(IDMessage.START)
        addOwnMessage(IDMessage.START_LUNCH)
        addOwnMessage(IDMessage.LUNCH_FINISHED)
        addOwnMessage(IDMessage.SUCCESS)
        addOwnMessage(IDMessage.FAILED)
    }



    override fun prepareReplication() {

    }


}

