package com.example.view

import com.example.app.Styles
import javafx.beans.property.SimpleStringProperty
import javafx.collections.ObservableList
import javafx.event.EventTarget
import javafx.scene.text.Font
import projects.covid.consumers.Consumer
import tornadofx.*

fun EventTarget.valuedTextView( text: String,
                            value: SimpleStringProperty) =
        hbox {
            spacing = 10.0
            vbox {
                spacer()
                text(text)
                        .addClass(Styles.appText)
            }

            text(value)
                    .addClass(Styles.appText)
                    .font = Font.font(16.0)
        }

fun EventTarget.statistics( text: String,
                                value: Array<Consumer>) =
        hbox {
            text(text)
                    .addClass(Styles.appText)

            vbox {
                value.forEach {
                    text("${it.calculateWorkload()}")
                            .addClass(Styles.appText)
                            .font = Font.font(16.0)
                }
            }
        }