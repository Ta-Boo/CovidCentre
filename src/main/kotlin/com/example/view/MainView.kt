package com.example.view

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
import javafx.scene.paint.Color
import javafx.scene.text.Font
import projects.covid.DOCTORS
import projects.covid.Utility
import projects.monteCarlo.ExponentialGenerator
import projects.monteCarlo.RangedGenerator
import tornadofx.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread


data class GraphData(
        val x: Int,
        val y: Float
)

class MainView : View("Covid 19") {
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


    var utility = Utility()
    val dateFormat = SimpleDateFormat("HH:mm")




    override val root = hbox {
        addClass(Styles.linearGradient)
        style {
            padding = box(20.px)
        }
        vbox {
            prefHeight = 10000.0
            spacing = 10.0
            hbox {
//                maxHeight = Double.MAX_VALUE
                prefHeight = 10000.0
                spacing = 10.0
                linechart("Average queue length", NumberAxis(), NumberAxis()) {
                    style {
                        minWidth = 120.px
                        prefWidth = 2400.px
                        maxWidth = Int.MAX_VALUE.px
                    }
                    series("Registration") {
                        animated = false
                        data.bind(registrationQueueLength) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                    series("Examination") {
                        animated = false
                        data.bind(examinationQueueLength) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                    series("Vaccination") {
                        animated = false
                        data.bind(vaccinationQueueLength) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                }
                linechart("Average waiting time", NumberAxis(), NumberAxis()) {
                    style {
                        minWidth = 120.px
                        prefWidth = 2400.px
                        maxWidth = Int.MAX_VALUE.px
                    }
                    series("Registration") {
                        animated = false
                        data.bind(registrationWaiting) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                    series("Examination") {
                        animated = false
                        data.bind(examinationWaiting) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                    series("Vaccination") {
                        animated = false
                        data.bind(vaccinationWaiting) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                }
            }
            hbox {
                spacing = 10.0
                maxHeight = Double.MAX_VALUE
                prefHeight = 10000.0
                linechart("Average work load", NumberAxis(), NumberAxis()) {
                    style {
                        minWidth = 120.px
                        prefWidth = 2400.px
                        maxWidth = Int.MAX_VALUE.px
                    }

                    series("Administrators") {
                        animated = false
                        data.bind(adminsWorkload) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                    series("Doctors") {
                        animated = false
                        data.bind(doctorsWorkload) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                    series("Nurses") {
                        animated = false
                        data.bind(nursesWorkload) {
                            XYChart.Data(it.x, it.y)
                        }
                    }
                }

                linechart("??", NumberAxis(), NumberAxis()) {
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
            }
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
                valuedTextView("Registration queue:", registrationQueueProperty)
                valuedTextView("busy admins:", bussyAdminsProperty)
                valuedTextView("Examination queue:", examinationQueueProperty)
                valuedTextView("busy doctors:", bussyDoctorsProperty)
                valuedTextView("Vaccination queue:", vaccinationQueueProperty)
                valuedTextView("busy nurses:", bussyNursesProperty)
                valuedTextView("Waiting room:", waitingRoomProperty)
                valuedTextView("Vaccinated:", donePatientsProperty)
                valuedTextView("Time:", timeProperty)
                spacer()
                text("Average waiting time") {
                    addClass(Styles.appText)
                    font = Font.font(18.0)
                }
                valuedTextView("Registration:", registrationWaitingTimeProperty)
                valuedTextView("Examination:", examinationWaitingTimeProperty)
                valuedTextView("Vaccination:", vaccinationWaitingTimeProperty)
                spacer()
                text("Average queue length") {
                    addClass(Styles.appText)
                    font = Font.font(18.0)
                }
                valuedTextView("Registration:", registrationQueueLengthProperty)
                valuedTextView("Examination:", examinationQueueLengthProperty)
                valuedTextView("Vaccination:", vaccinationQueueLengthProperty)
                valuedTextView("Monitoring:", monitoringProperty)
                spacer()
                text("Average work load") {
                    addClass(Styles.appText)
                    font = Font.font(18.0)
                }
                valuedTextView("Admins:", adminsWorkloadProperty)
                valuedTextView("Doctors:", doctorsWorkloadProperty)
                valuedTextView("Nurses:", nurseWorkLoadProperty)
                spacer()
                text("Interval 95%") {
                    addClass(Styles.appText)
                    font = Font.font(18.0)
                }
                valuedTextView("Left:", intervalLeftProp)
                valuedTextView("Right:", intervalRightProp)


            }
            spacer()

            slider = slider {
                min = 30.0
                max = 300.0
                value = 50.0
                style {

                    accentColor = AppColor.accent
                }
            }
            hbox {
                addClass(Styles.adaptiveWidth)
                spacing = 10.0
                button("Restart") {
                    prefWidth = 500.0
                    imageview("/restart.png") {
                        fitHeight = 20.0
                        fitWidth = 20.0
                    }
                    action {
                        restart()
                    }
                }

                button("Run/Pause") {
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

            button("Run replications") {
                imageview("/play_pause.png") {
                    fitHeight = 20.0
                    fitWidth = 20.0
                }
                action {
                    startReplications()
                }
            }
            hbox {
                spacing = 10.0
                 replicationsInput = textfield("500") {
                    promptText = "Replications"
                }

                minInput = textfield("1") {
                    promptText = "Min"
                }

                maxInput = textfield("20") {
                    promptText = "Max"
                }
            }
            button("Debug") {
                action {
                   startOptimization()
                }
            }

            hbox {
                spacer()
                spacing = -10.0
                vbox {
                    spacer()
                    text("") {
                        addClass(Styles.appText)
                        font = Font.font(9.0)
                        alignment = Pos.BOTTOM_RIGHT
                    }
                }

//                button {
//                    action {
//                        val runtime = Runtime.getRuntime()
//                        runtime.exec("open https://github.com/Ta-Boo")
//                    }
//                    style {
//                        padding = box(0.px)
//                        backgroundColor += Color.TRANSPARENT
//                        borderRadius += box(20.px)
//                    }
//                    imageview("/soul.png") {
//                        fitHeight = 40.0
//                        fitWidth = 40.0
//                    }
//                }
            }
        }
    }

    override fun onDock() {
        super.onDock()
        onLoad()
    }

    private fun onLoad() {
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

    fun restart() {
        utility = Utility()
        utility.beforeSimulationRun()
        onLoad()
    }

    private fun start() {
        thread {
            utility.runReplication {
                registrationQueue = utility.registrationQueue.container.size.toString()
                examinationQueue = utility.examinationQueue.container.size.toString()
                vaccinationQueue = utility.vaccinationQueue.container.size.toString()
                donePatients = utility.donePatients.patients.size.toString()
                bussyAdmins = utility.admins.filter { it.isBussy }.size.toString()
                bussyDoctors = utility.doctors.filter { it.isBussy }.size.toString()
                bussyNurses = utility.nurses.filter { it.isBussy }.size.toString()
                waitingRoom = utility.waitingRoom.container.size.toString()
                time = dateFormat.format(Date(1617429600000 + utility.actualTime * 1000))
                utility.delay = slider.value.toLong()
                val stats = utility.calculateSingleRunStatistics()
                registrationWaitingTime = stats.waitings[0].toString()
                examinationWaitingTime = stats.waitings[1].toString()
                vaccinationWaitingTime = stats.waitings[2].toString()

                registrationQueueLengthString = stats.queues[0].toString()
                examinationQueueLengthString = stats.queues[1].toString()
                vaccinationQueueLengthString = stats.queues[2].toString()
                monitoring = stats.queues[3].toString()

                adminsWorkloadString = stats.workload[0].toString()
                doctorsWorkloadString = stats.workload[1].toString()
                nurseWorkLoadString = stats.workload[2].toString()

                val interval = utility.interval()
                intervalLeft = interval.leftInterval().toString()
                intervalRight = interval.rightInterval().toString()
            }
        }

    }


    private fun startReplications() {
        utility = Utility()
        thread {
            utility.runReplications(1000) { replication ->
                if (replication % 100 == 0) {
                    val stats = utility.calculateDailyData()
                    registrationQueueLength.add(stats.queues[0])
                    examinationQueueLength.add(stats.queues[1])
                    vaccinationQueueLength.add(stats.queues[2])

                    adminsWorkload.add(stats.workload[0])
                    doctorsWorkload.add(stats.workload[1])
                    nursesWorkload.add(stats.workload[2])

                    registrationWaiting.add(stats.waitings[0])
                    examinationWaiting.add(stats.waitings[1])
                    vaccinationWaiting.add(stats.waitings[2])

                    registrationWaitingTime = stats.waitings[0].y.toString()
                    examinationWaitingTime = stats.waitings[1].y.toString()
                    vaccinationWaitingTime = stats.waitings[2].y.toString()

                    registrationQueueLengthString = stats.queues[0].y.toString()
                    examinationQueueLengthString = stats.queues[1].y.toString()
                    vaccinationQueueLengthString = stats.queues[2].y.toString()
                    monitoring = stats.queues[3].y.toString()


                    adminsWorkloadString = stats.workload[0].y.toString()
                    doctorsWorkloadString = stats.workload[1].y.toString()
                    nurseWorkLoadString = stats.workload[2].y.toString()

                    val interval = utility.interval()
                    intervalLeft = interval.leftInterval().toString()
                    intervalRight = interval.rightInterval().toString()
                    Thread.sleep(10)
                }
            }
        }
    }

    private fun  startOptimization() {
        utility = Utility()
        optimization.removeAll()
        val fps = replicationsInput.text.toInt()/(maxInput.text.toInt() - minInput.text.toInt())

        thread {
            var doctors = minInput.text.toInt()
            DOCTORS = doctors
            utility.runReplications(replicationsInput.text.toInt()) {
                if (it % fps == 0) {
                    val stats = utility.calculateDailyData()
                    optimization.add(GraphData(doctors, stats.queues[1].y))
                    doctors++
                    DOCTORS = doctors
                    Thread.sleep(50)
                    utility.resetStats()
                }
            }
        }
    }
}


