package com.example.app

import com.example.view.CovidMainView
import com.example.view.MainView
import tornadofx.*

class CovidApp: App(CovidMainView::class, CovidStyles::class) {

    init {
        importStylesheet(CovidStyles::class)
    }
}