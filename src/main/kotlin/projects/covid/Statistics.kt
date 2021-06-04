package projects.covid

import com.example.view.GraphData
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.pow
import kotlin.math.sqrt


data class UIData(
        val workload: Array<GraphData>,
        val queues: Array<GraphData>,
        val waitings: Array<GraphData>
)

data class UIStats(
        val workload: Array<Float>,
        val queues: Array<Float>,
        val waitings: Array<Float>
)


class Statistics(val utility: Utility) {
    val registrationQueueLength = AverageStat()
    val examinationQueueLength = AverageStat()
    val vaccinationQueueLength = AverageStat()

    val registrationWaiting = AverageStat()
    val examinationWaiting = AverageStat()
    val vaccinationWaiting = AverageStat()

    val dailyRegistrationQueueLength = AverageStat()
    val dailyExaminationQueueLength = AverageStat()
    val dailyVaccinationQueueLength = AverageStat()

    val dailyAdminsWorkload = AverageStat()
    val dailyDoctorsWorkload = AverageStat()
    val dailyNursesWorkload = AverageStat()

    val dailyRegistrationWaiting = AverageStat()
    val dailyExaminationWaiting = AverageStat()
    val dailyVaccinationWaiting = AverageStat()

    val waitingRoom = AverageStat()

    fun constructDailyLengthData(): Array<GraphData> {
        return arrayOf(dailyRegistrationQueueLength, dailyExaminationQueueLength, dailyVaccinationQueueLength, waitingRoom)
                .map {
                    GraphData(utility.replication, it.average.toFloat())
                }.toTypedArray()
    }


    fun constructDailyWaitingData(): Array<GraphData> {
        return arrayOf(dailyRegistrationWaiting, dailyExaminationWaiting, dailyVaccinationWaiting).map {
            GraphData(utility.replication, it.average.toFloat())
        }.toTypedArray()
    }

    fun constructDailyWorkloadData(): Array<GraphData> {
        return arrayOf(dailyAdminsWorkload, dailyDoctorsWorkload, dailyNursesWorkload)
                .map {
                    GraphData(utility.replication, it.average.toFloat())
                }.toTypedArray()
    }

    fun constructWaitingStats(): Array<Float> {
        return arrayOf(registrationWaiting, examinationWaiting, vaccinationWaiting).map {
            it.average.toFloat()
        }.toTypedArray()
    }

    fun constructLengthStats(): Array<Float> {
        return arrayOf(registrationQueueLength, examinationQueueLength, vaccinationQueueLength, waitingRoom).map {
            it.average.toFloat()
        }.toTypedArray()
    }

    fun constructWorkloadStats(): Array<Float> {
        return arrayOf(dailyAdminsWorkload, dailyDoctorsWorkload, dailyNursesWorkload).map {
            it.average.toFloat()
        }.toTypedArray()
    }

    fun saveDaily() {
        dailyAdminsWorkload.addValue(utility.admins.map { it.calculateWorkload().toDouble() }.sum() / utility.admins.size)
        dailyDoctorsWorkload.addValue(utility.doctors.map { it.calculateWorkload().toDouble() }.sum() / utility.doctors.size)
        dailyNursesWorkload.addValue(utility.nurses.map { it.calculateWorkload().toDouble() }.sum() / utility.nurses.size)

        dailyRegistrationQueueLength.addValue(registrationQueueLength.average)
        dailyExaminationQueueLength.addValue(examinationQueueLength.average)
        dailyVaccinationQueueLength.addValue(vaccinationQueueLength.average)

        dailyRegistrationWaiting.addValue(registrationWaiting.average)
        dailyExaminationWaiting.addValue(examinationWaiting.average)
        dailyVaccinationWaiting.addValue(vaccinationWaiting.average)
    }


    fun reset() {
        arrayOf(registrationQueueLength,
                examinationQueueLength,
                vaccinationQueueLength,

                registrationWaiting,
                examinationWaiting,
                vaccinationWaiting)
                .forEach {
                    it.reset()
                }
    }
}

open class AverageStat {
    var sumSquared = 0.0
    var totalMeasurements = 0.0
    var sum = 0.0
    val confidence = 0.95
    var average = 0.0
        get() {
            return (sum / totalMeasurements.toFloat()).let { if (it.isNaN()) 0.0 else it }
        }

    fun addValue(value: Int) {
        addValue(value.toDouble())
    }

    fun addValue(value: Double) {
        sum += value
        sumSquared += value.pow(2)
        totalMeasurements ++
    }

    private fun std() =
            if (this.totalMeasurements == 0.0) {
                0.0
            } else {
                val x = abs(1.0 / (totalMeasurements - 1) * sumSquared)
                val y =  (1.0 / (totalMeasurements - 1) * sum).pow(2.0)
                sqrt(
                        abs((1.0 / (totalMeasurements - 1) * sumSquared)
                                -
                                (1.0 / (totalMeasurements - 1) * sum).pow(2.0)
                        ))
            }


    fun rightInterval() =
            if (this.totalMeasurements == 0.0) {
                0.0
            } else {
                sum / totalMeasurements + (confidence * std()/ sqrt(totalMeasurements))
            }

    fun leftInterval() =
            if (this.totalMeasurements == 0.0) {
                0.0
            } else {
                sum / totalMeasurements - (confidence * std()/ sqrt(totalMeasurements))
            }

    fun reset() {
        totalMeasurements = 0.0
        sum = 0.0
        average = 0.0
        sumSquared = 0.0

    }
}

class AverageQueueStats  {

    var lastSize = 0.0
    var lastTime = 0
    var sumSquared = 0.0
    var totalMeasurements = 0.0
    var sum = 0.0
    val confidence = 0.95

//    fun addValue(value: Int, time: Int) {
//        addValue(value.toDouble(), time)
//    }

    fun addValue(value: Double, time: Int) {
//        sum += (lastSize * (time - lastTime))
//        sumSquared += (lastSize * (time - lastTime)).pow(2)
//        totalMeasurements += (time - lastTime)
//        lastSize = value
//        lastTime = time

        sum += value
        sumSquared += value.pow(2)
        totalMeasurements ++

    }

    var average = 0.0
        get() {
            return (sum / totalMeasurements.toFloat()).let { if (it.isNaN()) 0.0 else it }
        }

    private fun std() =
            if (this.totalMeasurements == 0.0) {
                0.0
            } else {
                val x = abs(1.0 / (totalMeasurements - 1) * sumSquared)
                val y =  (1.0 / (totalMeasurements - 1) * sum).pow(2.0)
                sqrt(
                        abs((1.0 / (totalMeasurements - 1) * sumSquared)
                                -
                                (1.0 / (totalMeasurements - 1) * sum).pow(2.0)
                ))
            }


    fun rightInterval() =
            if (this.totalMeasurements == 0.0) {
                0.0
            } else {
                sum / totalMeasurements + (confidence * std()/ sqrt(totalMeasurements))
            }

    fun leftInterval() =
            if (this.totalMeasurements == 0.0) {
                0.0
            } else {
                sum / totalMeasurements - (confidence * std()/ sqrt(totalMeasurements))
            }
}