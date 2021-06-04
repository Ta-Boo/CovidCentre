package projects.agentOrientedCovid

import tornadofx.*
import kotlin.reflect.KType
import kotlin.reflect.full.memberProperties

object Workers {
//MARK: #1
    var ADMINS                            = 4
    var DOCTORS                           = 6
    var NURSES                            = 3
    const val PATIENTS                    = 540
    const val ARRIVAL_INTERVAL            = 60.0
    const val EARLY_ARRIVALS              = false

//MARK: #2
//    var ADMINS                            = 14
//    var DOCTORS                           = 20
//    var NURSES                            = 7
//    const val PATIENTS                    = 1700
//    const val ARRIVAL_INTERVAL            = 19.06
//    const val EARLY_ARRIVALS              = false

//MARK: #3
//    var ADMINS                            = 14
//    var DOCTORS                           = 20
//    var NURSES                            = 7
//    const val PATIENTS                    = 1700
//    const val ARRIVAL_INTERVAL            = 19.06
//    const val EARLY_ARRIVALS              = true

    const val TIME                              = 32_400.0
}



object IDMessage: IdList() {
    const val START                             = 1
    const val PATIENT_ARRIVED                   = 2
    const val ARRIVAL_SCHEDULER_FINISHED        = 3

    const val START_REGISTRATION                = 10
    const val PATIENT_REGISTERED                = 11
    const val STARTED_REGISTRATION_QUEUE        = 12
    const val ENDED_REGISTRATION_QUEUE          = 13

    const val START_EXAMINATION                 = 20
    const val PATIENT_EXAMINATED                = 21
    const val STARTED_EXAMINATION_QUEUE         = 22
    const val ENDED_EXAMINATION_QUEUE           = 23

    const val START_VACCINATION                 = 30
    const val PATIENT_VACCINATED                = 31
    const val STARTED_VACCINATION_QUEUE         = 32
    const val ENDED_VACCINATION_QUEUE           = 33

    const val START_WAITING                     = 40
    const val END_WAITING                       = 41
    const val WAITING_FINISHED                  = 42

    const val TRANSPORT_INJECTION_START         = 80
    const val TRANSPORT_INJECTION_FINISHED      = 81
    const val TRANSPORT_INJECTION_BACK_START         = 800
    const val TRANSPORT_INJECTION_BACK_FINISHED      = 810

    const val TRANSPORT_EXAMINATION_STARTED     = 90
    const val TRANSPORT_EXAMINATION_FINISHED    = 91
    const val TRANSPORT_EXAMINATION_START       = 92

    const val TRANSPORT_VACCINATION_STARTED     = 93
    const val TRANSPORT_VACCINATION_FINISHED    = 94
    const val TRANSPORT_VACCINATION_START       = 95

    const val TRANSPORT_WAITING_STARTED         = 96
    const val TRANSPORT_WAITING_FINISHED        = 97
    const val TRANSPORT_WAITING_START           = 98

    const val LUNCH_ADMINS_START                = 100
//    const val LUNCH_DOCTORS_START                = 101
//    const val LUNCH_NURSES_START                = 102
    const val START_LUNCH                       = 103
    const val LUNCH_FINISHED                       = 104
    const val ASK_FOR_LUNCH                       = 105
    const val SEND_FOR_LUNCH                       = 106

    const val INJECTIONS_PREPARE                       = 300
    const val INJECTIONS_PREPARE_FINISHED                       = 3001
    const val INJECTIONS_PREPARED                     = 3002


    const val FAILED = 9999
    const val SUCCESS = 10000
    const val STOP = 10001



}

object IDAgent: IdList() {
    const val SURROUNDING                       = 1
    const val ARRIVAL_SCHEDULER                 = 2
    const val SURROUNDING_MANAGER               = 3

    const val BOSS                              = 10
    const val BOSS_MANAGER                      = 11

    const val ADMIN_MANAGER                     = 100
    const val ADMIN_AGENT                       = 101
    val ADMIN_ASSISTANTS                        = IntArray(Workers.ADMINS) {102 + it}

    const val DOCTOR_MANAGER                    = 200
    const val DOCTOR_AGENT                      = 201
    var DOCTOR_ASSISTANTS                       = IntArray(Workers.DOCTORS) {202 + it}

    const val NURSE_MANAGER                     = 300
    const val NURSE_AGENT                       = 301
    val NURSE_ASSISTANTS                        = IntArray(Workers.NURSES) {302 + it}

    const val WAITING_MANAGER                   = 400
    const val WAITING_AGENT                     = 401
    const val WAITING_ASSISTANT                 = 402

    const val LUNCH_MANAGER                   = 500
    const val LUNCH_AGENT                     = 501
    const val LUNCH_ASSISTANT_ADMINS          = 502
    const val LUNCH_ASSISTANT_DOCTORS         = 503
    const val LUNCH_ASSISTANT_NURSES          = 504
    const val LUNCH_ASSISTANT_COORDINATOR_ADMINS          = 505
    const val LUNCH_ASSISTANT_COORDINATOR_DOCTORS         = 506
    const val LUNCH_ASSISTANT_COORDINATOR_NURSES          = 507

    const val INJECTIONS_MANAGER                   = 600
    const val INJECTIONS_AGENT                     = 601
    const val INJECTIONS_ASSISTANT                 = 602

    const val TRANSPORT_MANAGER                 = 900
    const val TRANSPORT_AGENT                   = 901
    const val TRANSPORT_ASSISTANT_EXAMINATION   = 903
    const val TRANSPORT_ASSISTANT_VACCINATION   = 904
    const val TRANSPORT_ASSISTANT_WAITING       = 905
    const val TRANSPORT_ASSISTANT_INJECTIONS    = 906
    const val TRANSPORT_ASSISTANT_BACK_INJECTIONS    = 907

}

open class IdList {
    val start                                   = 2147483647
    val finish                                  = 2147483646
    val breakCA                                 = 2147483645
    val goal                                    = 2147483644
    val done                                    = 2147483643
    val transfer                                = 2147483642
    val cancel                                  = 2147483641
    val handoverDA                              = 2147483640
    val entrustDA                               = 2147483639
    val returnDA                                = 2147483638
    val undefined                               = 2147483637
    val stopSim                                 = 2147483636
    val pauseSim                                = 2147483635
    val stopReplication                         = 2147483634
    val startSlowdown                           = 2147483633
    val slowdownAgentId                         = 2147483647
    val slowdownManagerId                       = 2147483646
    val slowdownProcessId                       = 2147483645
    val absorbtion                              = -1
}
