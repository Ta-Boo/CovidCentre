package com.example.app

import com.example.view.MainView
import tornadofx.*

class MyApp: App(MainView::class, Styles::class) {

    init {
        importStylesheet(Styles::class)
    }
}