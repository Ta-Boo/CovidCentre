package projects.agentOrientedCovid

import OSPABA.Simulation
import OSPABA.SlowdownAgent
import com.sun.corba.se.spi.orbutil.threadpool.Work
import projects.agentOrientedCovid.admins.AdminAgent
import projects.agentOrientedCovid.doctors.DoctorAgent
import projects.agentOrientedCovid.boss.BossAgent
import projects.agentOrientedCovid.lunch.LunchAgent
import projects.agentOrientedCovid.lunch.LunchAssistant
import projects.agentOrientedCovid.nurses.NurseAgent
import projects.agentOrientedCovid.nurses.injections.InjectionsAgent
import projects.agentOrientedCovid.surrounding.SurroundingAgent
import projects.agentOrientedCovid.transports.TransportAgent
import projects.agentOrientedCovid.waitingRoom.WaitingAgent
import sun.tools.jconsole.Worker
import java.util.function.Consumer

class CovidSimulation(): Simulation() {
    val boss = BossAgent(this)
    val surroundingAgent = SurroundingAgent(this, boss)
    val adminAgent = AdminAgent(this, boss)
    val doctorAgent = DoctorAgent(this, boss)
    val nurseAgent = NurseAgent(this, boss)
    val waitingAgent = WaitingAgent(this, boss)
    val injectionsAgent = InjectionsAgent(this, boss)
    val transportAgent = TransportAgent(this, boss)
    val lunchAgent = LunchAgent(this, boss)

    override fun prepareReplication() {
        super.prepareReplication()
        boss.runReplication()
    }

    override fun prepareSimulation() {
        super.prepareSimulation()
    }


    override fun replicationFinished() {
        super.replicationFinished()

//        println("messages left : ${boss.allMessageCount()}")
//        println("REG queue = ${adminAgent.queue.lengthStatistic().mean()}")
//        println("REG waiting = ${adminAgent.waitingStats.mean()}")
//        println("DOC queue = ${doctorAgent.queue.lengthStatistic().mean()}")
//        println("DOC waiting = ${doctorAgent.waitingStats.mean()}")
//        println("DOC workload = ${doctorAgent.actualWorkload()}")
//        println("NUR queue = ${nurseAgent.queue.lengthStatistic().mean()}")
//        println("NUR waiting = ${nurseAgent.waitingStats.mean()}")
//        println("NUR workload = ${nurseAgent.actualWorkload()}")
//
//        println("REG (queue)= ${adminAgent.queue.count()} (admins) = ${adminAgent.assistants.filter { it.isBusy() }.count()}/${adminAgent.assistants.count()}")
//        println("DOC (queue)= ${doctorAgent.queue.count()} (doctors) = ${doctorAgent.assistants.filter { it.isBusy() }.count()}/${doctorAgent.assistants.count()}")
//        println("NURSES (queue)= ${nurseAgent.queue.count()} (doctors) = ${nurseAgent.assistants.filter { it.isBusy() }.count()}/${nurseAgent.assistants.count()}")
//        println("WAITING (room)= ${waitingAgent.count} ")
    }


    override fun stopReplication() {
        super.stopReplication()
        arrayOf(adminAgent, doctorAgent, nurseAgent, waitingAgent).forEach {
            it.afterReplication()
        }

    }
    override fun simulationFinished() {
        super.simulationFinished()
    }
}