package projects.agentOrientedCovid.surrounding

import OSPABA.Agent
import OSPABA.ContinualAssistant
import OSPABA.MessageForm
import OSPABA.Simulation
import OSPRNG.EmpiricPair
import OSPRNG.EmpiricRNG
import OSPRNG.UniformDiscreteRNG
import projects.agentOrientedCovid.IDAgent
import projects.agentOrientedCovid.IDMessage
import projects.agentOrientedCovid.Workers
import java.util.*
import kotlin.math.max
import kotlin.math.min


class SurroundingAssistant(
        simulation: Simulation,
        agent: Agent
) : ContinualAssistant(
        IDAgent.ARRIVAL_SCHEDULER,
        simulation,
        agent
) {
    private val interval = Workers.ARRIVAL_INTERVAL
    private val arrivals = generateArrivals()
    var count = Workers.PATIENTS
    @Suppress("DUPLICATE_LABEL_IN_WHEN")
    override fun processMessage(message: MessageForm) {
        when (message.code()) {
            IDMessage.start -> {
                message.setCode(IDMessage.PATIENT_ARRIVED)
                hold(0.0, message)
            }

            IDMessage.PATIENT_ARRIVED -> {
                count--
                if (count > 0) {
                    val copy = message.createCopy()

                    val delay = if (Workers.EARLY_ARRIVALS) arrivals.pop() - mySim().currentTime() else interval
                    hold(delay, copy)
                }
                assistantFinished(message)
            }
        }
    }


    fun generateArrivals(): LinkedList<Double> {
        val generator = EmpiricRNG(
                EmpiricPair(UniformDiscreteRNG(60, 1200), .3),
                EmpiricPair(UniformDiscreteRNG(1200, 3600), .4),
                EmpiricPair(UniformDiscreteRNG(3600, 4800), .2),
                EmpiricPair(UniformDiscreteRNG(4800, 14400), .1)
        )
        val probGenerator = Random()
        return LinkedList(
                Array(Workers.PATIENTS) { it * interval }.map {
                    if(probGenerator.nextDouble() > 0.1) max(0.0, it - generator.sample().toDouble()) else it
                }.sorted())
    }

    override fun prepareReplication() {
        super.prepareReplication()
        arrivals.clear()
        arrivals.addAll(generateArrivals())
    }
}