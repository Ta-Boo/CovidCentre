package com.example.view

import OSPABA.Simulation
import com.example.app.AppColor
import com.example.app.Styles
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.geometry.Pos
import javafx.scene.chart.NumberAxis
import javafx.scene.chart.XYChart
import javafx.scene.control.Slider
import javafx.scene.control.TextField
import javafx.scene.layout.VBox
import javafx.scene.text.Font
import projects.agentOrientedCovid.CovidSimulation
import projects.agentOrientedCovid.Workers
import projects.agentOrientedCovid.app.view.roundUI
import projects.covid.DOCTORS
import projects.covid.Utility
import tornadofx.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread
import kotlin.math.max
import kotlin.math.min

class CovidMainView : View("Covid 19") {
    var registrationQueueLength = FXCollections.observableArrayList<GraphData>()
    var examinationQueueLength = FXCollections.observableArrayList<GraphData>()
    var vaccinationQueueLength = FXCollections.observableArrayList<GraphData>()

    var adminsWorkload = FXCollections.observableArrayList<GraphData>()
    var nursesWorkload = FXCollections.observableArrayList<GraphData>()
    var doctorsWorkload = FXCollections.observableArrayList<GraphData>()

    var registrationWaiting = FXCollections.observableArrayList<GraphData>()
    var examinationWaiting = FXCollections.observableArrayList<GraphData>()
    var vaccinationWaiting = FXCollections.observableArrayList<GraphData>()

    var optimization = FXCollections.observableArrayList<GraphData>()

    var slider: Slider by singleAssign()
    var singleRunBox: VBox by singleAssign()

    var replicationsInput: TextField by singleAssign()
    var minInput: TextField by singleAssign()
    var maxInput: TextField by singleAssign()

    var registrationQueueProperty = SimpleStringProperty()
    var registrationQueue by registrationQueueProperty

    var examinationQueueProperty = SimpleStringProperty()
    var examinationQueue by examinationQueueProperty

    var vaccinationQueueProperty = SimpleStringProperty()
    var vaccinationQueue by vaccinationQueueProperty

    var bussyAdminsProperty = SimpleStringProperty()
    var bussyAdmins by bussyAdminsProperty

    var bussyDoctorsProperty = SimpleStringProperty()
    var bussyDoctors by bussyDoctorsProperty

    var bussyNursesProperty = SimpleStringProperty()
    var bussyNurses by bussyNursesProperty

    var waitingRoomProperty = SimpleStringProperty()
    var waitingRoom by waitingRoomProperty

    var donePatientsProperty = SimpleStringProperty()
    var donePatients by donePatientsProperty

    var timeProperty = SimpleStringProperty()
    var time by timeProperty

    var registrationWaitingTimeProperty = SimpleStringProperty()
    var registrationWaitingTime by registrationWaitingTimeProperty

    var examinationWaitingTimeProperty = SimpleStringProperty()
    var examinationWaitingTime by examinationWaitingTimeProperty

    var vaccinationWaitingTimeProperty = SimpleStringProperty()
    var vaccinationWaitingTime by vaccinationWaitingTimeProperty

    var registrationQueueLengthProperty = SimpleStringProperty()
    var examinationQueueLengthProperty = SimpleStringProperty()
    var vaccinationQueueLengthProperty = SimpleStringProperty()
    var registrationQueueLengthString by registrationQueueLengthProperty
    var examinationQueueLengthString by examinationQueueLengthProperty
    var vaccinationQueueLengthString by vaccinationQueueLengthProperty

    var adminsWorkloadProperty = SimpleStringProperty()
    var doctorsWorkloadProperty = SimpleStringProperty()
    var nurseWorkLoadProperty = SimpleStringProperty()
    var adminsWorkloadString by adminsWorkloadProperty
    var doctorsWorkloadString by doctorsWorkloadProperty
    var nurseWorkLoadString by nurseWorkLoadProperty

    var intervalLeftProp = SimpleStringProperty()
    var intervalLeft by intervalLeftProp

    var intervalRightProp = SimpleStringProperty()
    var intervalRight by intervalRightProp

    var monitoringProperty = SimpleStringProperty()
    var monitoring by monitoringProperty


    var simulation = CovidSimulation()
    val dateFormat = SimpleDateFormat("HH:mm")




    override val root = hbox {
        addClass(Styles.linearGradient)
        style {
            padding = box(20.px)
        }
        linechart("Dependency of queue length on doctors count", NumberAxis(), NumberAxis()) {
            style {
                minWidth = 120.px
                prefWidth = 2400.px
                maxWidth = Int.MAX_VALUE.px
            }

            series("Optimization") {
                animated = false
                data.bind(optimization) {
                    XYChart.Data(it.x, it.y)
                }
            }
        }

        vbox {
            addClass(Styles.adaptiveWidth)
            spacing = 12.0
            style {
                padding = box(0.px, 0.px, 0.px, 20.px)
            }
            hbox {
                addClass(Styles.adaptiveWidth)
                spacing = 10.0
                vbox {
                    addClass(Styles.adaptiveWidth)
                    style {
                        spacing = 10.px
                        minWidth = 300.px
                        padding = box(0.px, 0.px, 0.px, 20.px)
                    }
                    text("Actual State") {
                        addClass(Styles.appText)
                        font = Font.font(22.0)
                    }

                    valuedTextView("Registration queue:", registrationQueueProperty)
                    valuedTextView("busy admins:", bussyAdminsProperty)
                    valuedTextView("Examination queue:", examinationQueueProperty)
                    valuedTextView("busy doctors:", bussyDoctorsProperty)
                    valuedTextView("Vaccination queue:", vaccinationQueueProperty)
                    valuedTextView("busy nurses:", bussyNursesProperty)
                    valuedTextView("Waiting room:", waitingRoomProperty)
                    valuedTextView("Vaccinated:", donePatientsProperty)
                    valuedTextView("Time:", timeProperty)

                    text("95% confidence interval\n(waiting room)") {
                        addClass(Styles.appText)
                        font = Font.font(22.0)
                    }
                    valuedTextView("Left:", intervalLeftProp)
                    valuedTextView("Right:", intervalRightProp)

                }
                vbox {
                    addClass(Styles.adaptiveWidth)
                    style {
                        spacing = 10.px
                        minWidth = 300.px
                        padding = box(0.px, 0.px, 0.px, 20.px)

                    }

                    singleRunBox = vbox {
                        spacing = 10.0
                        maxWidth = Double.MAX_VALUE

                        text("Average waiting time") {
                            addClass(Styles.appText)
                            font = Font.font(22.0)
                        }
                        valuedTextView("Registration:", registrationWaitingTimeProperty)
                        valuedTextView("Examination:", examinationWaitingTimeProperty)
                        valuedTextView("Vaccination:", vaccinationWaitingTimeProperty)
                        valuedTextView("Monitoring:", monitoringProperty)
                        spacer()
                        text("Average queue length") {
                            addClass(Styles.appText)
                            font = Font.font(22.0)
                        }
                        valuedTextView("Registration:", registrationQueueLengthProperty)
                        valuedTextView("Examination:", examinationQueueLengthProperty)
                        valuedTextView("Vaccination:", vaccinationQueueLengthProperty)
                        spacer()
                        text("Average work load") {
                            addClass(Styles.appText)
                            font = Font.font(22.0)
                        }
                        valuedTextView("Admins:", adminsWorkloadProperty)
                        valuedTextView("Doctors:", doctorsWorkloadProperty)
                        valuedTextView("Nurses:", nurseWorkLoadProperty)
                    }

                }
            }
            spacer()
            slider = slider {
                min = .5
                max = 1.0
                value = .90
                style {
                    accentColor = AppColor.accent
                }
            }
            hbox {
                addClass(Styles.adaptiveWidth)
                spacing = 10.0
                button("Pause/Resume") {
                    prefWidth = 500.0
                    imageview("/play_pause.png") {
                        fitHeight = 20.0
                        fitWidth = 20.0
                    }
                    action {
                        playOrPause()
                    }
                }

                button("Run") {
                    prefWidth = 500.0
                    imageview("/play_pause.png") {
                        fitHeight = 20.0
                        fitWidth = 20.0
                    }
                    action {
                        start()
                    }
                }
            }

            hbox {
                addClass(Styles.adaptiveWidth)
                spacing = 10.0
                button("Run replications") {
                    prefWidth = 500.0
                    imageview("/play_pause.png") {
                        fitHeight = 20.0
                        fitWidth = 20.0
                    }
                    action {
                        startReplications()
                    }
                }
                button("Restart") {
                    prefWidth = 500.0
                    imageview("/restart.png") {
                        fitHeight = 20.0
                        fitWidth = 20.0
                    }
                    action {
                        simulation = CovidSimulation()
                        simulation.setSimSpeed(60.0, 0.035)
                    }
                }
            }


            button("Examination queue dependency") {
                action {
                    optimize()
                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        onLoad()
    }

    private fun onLoad() {
        simulation.setSimSpeed(60.0, 0.035)
        registrationQueue = "0"
        examinationQueue = "0"
        vaccinationQueue = "0"
        donePatients = "0"
        bussyAdmins = "0"
        bussyDoctors = "0"
        bussyNurses = "0"
        time = dateFormat.format(Date(1617429600000))
        examinationQueueLength.clear()
        registrationQueueLength.clear()
        vaccinationQueueLength.clear()

        adminsWorkload.clear()
        nursesWorkload.clear()
        doctorsWorkload.clear()

        registrationWaiting.clear()
        examinationWaiting.clear()
        vaccinationWaiting.clear()

        optimization.clear()

    }

    fun playOrPause() {
        if (simulation.isPaused) {
            simulation.resumeSimulation()
        } else {
            simulation.pauseSimulation()
        }
    }

    private fun start() {
        thread {
            var frame = 0
            simulation.onRefreshUI {
                frame++
                registrationQueue = simulation.adminAgent.queue.count().toString()
                examinationQueue = simulation.doctorAgent.queue.count().toString()
                vaccinationQueue = simulation.nurseAgent.queue.count().toString()
                donePatients = simulation.boss.manager.counter.toString()
                bussyAdmins = simulation.adminAgent.assistants.filter { it.isBusy() }.size.toString()
                bussyDoctors = simulation.doctorAgent.assistants.filter { it.isBusy() }.size.toString()
                bussyNurses = simulation.nurseAgent.assistants.filter { it.isBusy() }.size.toString()
                waitingRoom = simulation.waitingAgent.count.toString()

                registrationWaitingTime = simulation.adminAgent.waitingStats.mean().roundUI(3)
                examinationWaitingTime = simulation.doctorAgent.waitingStats.mean().roundUI(3)
                vaccinationWaitingTime = simulation.nurseAgent.waitingStats.mean().roundUI(3)

                registrationQueueLengthString = simulation.adminAgent.queue.lengthStatistic().mean().roundUI(3)
                examinationQueueLengthString = simulation.doctorAgent.queue.lengthStatistic().mean().roundUI(3)
                vaccinationQueueLengthString = simulation.nurseAgent.queue.lengthStatistic().mean().roundUI(3)
                monitoring = simulation.waitingAgent.stats.mean().roundUI(6)
                if (frame% 20 == 0) {
                    adminsWorkloadString = simulation.adminAgent.actualWorkload().roundUI(3)
                    doctorsWorkloadString = simulation.doctorAgent.actualWorkload().roundUI(3)
                    nurseWorkLoadString = simulation.nurseAgent.actualWorkload().roundUI(3)

                    if(it.currentTime() > 1000) {
                        intervalLeft = simulation.waitingAgent.stats.confidenceInterval_95()[0].roundUI(3)
                        intervalRight = simulation.waitingAgent.stats.confidenceInterval_95()[1].roundUI(3)
                    }
                }

                time = dateFormat.format(Date(1617429600000 + simulation.currentTime().toLong() * 1000))
                simulation.setSimSpeed(180.0, 1.0 - slider.value)
            }
            simulation.simulate(1,  Workers.TIME)
        }
    }


    private fun startReplications() {
        simulation = CovidSimulation()
        simulation.setMaxSimSpeed()
        val notAvailable = "Ø(replications)"
        thread {
            var replication = 0
            simulation.onReplicationDidFinish {
                replication++
//                if (replication % 50 != 0) {
//                    return@onReplicationDidFinish
//                }
                registrationQueue = notAvailable
                examinationQueue = notAvailable
                vaccinationQueue = notAvailable
                donePatients = notAvailable
                bussyAdmins = notAvailable
                bussyDoctors = notAvailable
                bussyNurses = notAvailable
                waitingRoom = notAvailable

                registrationQueueLengthString = simulation.adminAgent.persistentQueueData.mean().roundUI()
                examinationQueueLengthString = simulation.doctorAgent.persistentQueueData.mean().roundUI()
                vaccinationQueueLengthString = simulation.nurseAgent.persistentQueueData.mean().roundUI()


                registrationWaitingTime = simulation.adminAgent.persistentWaitingData.mean().roundUI()
                examinationWaitingTime = simulation.doctorAgent.persistentWaitingData.mean().roundUI()
                vaccinationWaitingTime = simulation.nurseAgent.persistentWaitingData.mean().roundUI()
                monitoring = simulation.waitingAgent.persistentStats.mean().roundUI()

                registrationQueueLengthString = simulation.adminAgent.persistentQueueData.mean().roundUI()
                examinationQueueLengthString = simulation.doctorAgent.persistentQueueData.mean().roundUI()
                vaccinationQueueLengthString = simulation.nurseAgent.persistentQueueData.mean().roundUI()

                adminsWorkloadString = simulation.adminAgent.persistentWorkload.mean().roundUI(3)
                doctorsWorkloadString = simulation.doctorAgent.persistentWorkload.mean().roundUI(3)
                nurseWorkLoadString = simulation.nurseAgent.persistentWorkload.mean().roundUI(3)

                if (replication > 2) {
                    intervalLeft = simulation.waitingAgent.persistentStats.confidenceInterval_95()[0].roundUI(3)
                    intervalRight = simulation.waitingAgent.persistentStats.confidenceInterval_95()[1].roundUI(3)
                }

                time = notAvailable
            }
            simulation.simulate(1500,  Workers.TIME)
        }
    }

    private fun  optimize() {
        optimization.clear()
        thread {
//            var doctors = minInput.text.toInt()
            val replications = 250
            val minDocs = 1
            val maxDocs = 15
            val step = replications / (maxDocs - minDocs)
            val iterations = maxDocs - minDocs
            var doctors = minDocs

            (minDocs..maxDocs).forEach {
                Workers.DOCTORS = doctors
                simulation = CovidSimulation()
//                simulation.setMaxSimSpeed()
                simulation.onSimulationDidFinish {
                    optimization.add(GraphData(doctors, simulation.doctorAgent.persistentQueueData.mean().toFloat()))
                    Thread.sleep(500)
                }
                simulation.simulate(step)
                doctors++
            }
        }
    }

}


