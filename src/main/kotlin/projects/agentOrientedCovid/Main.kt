package projects.agentOrientedCovid

import OSPABA.Agent
import OSPABA.Manager
import OSPABA.MessageForm
import OSPABA.Simulation
import projects.agentOrientedCovid.boss.BossAgent
import kotlin.reflect.full.memberProperties

fun main(args: Array<String>){
    val simulation = CovidSimulation()
    simulation.simulate(1,  Workers.TIME*2)
}

//MARK: MESSAGES
class Message: MessageForm {
    var worker: Int
    var time: Double

    constructor(mySim: Simulation?) : super(mySim) {
        worker = -1
        time = 0.0
    }

    constructor(original: Message) : super(original) {
        worker = original.worker
        time = original.time
    }

    override fun createCopy(): MessageForm {
        return Message(this)
    }
}

//MARK: AGENTS

fun MessageForm.msg() = this as Message

var MessageForm.msg: Message
    get() = this as Message
    set(value) = TODO()

