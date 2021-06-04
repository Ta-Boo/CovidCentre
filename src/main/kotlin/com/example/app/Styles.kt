package com.example.app

import javafx.scene.Cursor
import javafx.scene.paint.*
import javafx.scene.text.FontWeight
import tornadofx.*
import java.awt.Paint

object AppColor {
    val primary: Color = Color.web("#4b2b31")
    val primaryTransparent: Color = Color.web("#264b2b31")
    val secondary: Color = Color.web("#6f3637")
    val accent: Color = Color.web("#233342")
    val text: Color = Color.web("#EDEBEB")
    val secondaryGradientStart: Color = Color.web("#b35340")
    val secondaryGradientEnd: Color = Color.web("#ce8054")
}




class Styles : Stylesheet() {
    companion object {
        val heading by cssclass()
        val linearGradient by cssclass()
        val roundedCorners by cssclass()
        val adaptiveWidth by cssclass()
        val appText by cssclass()
    }

    init {
        label and heading {
            padding = box(10.px)
            fontSize = 20.px
            fontWeight = FontWeight.BOLD
        }

        chart {
            createSymbols = false
//            chartSeriesLine {
//                stroke = AppColor.accent
//            }
            backgroundRadius += box(16.0.px)
            backgroundColor  += LinearGradient(0.5,
                        0.0,
                        0.5,
                        1.0,
                        true,
                        CycleMethod.REFLECT,
                        Stop(0.0, AppColor.secondaryGradientStart),
                        Stop(1.0, AppColor.secondaryGradientEnd)
            )
            chartTitle {
                textFill = AppColor.text
            }

            axis {
                tickLabelFill = AppColor.text
            }

            chartHorizontalGridLines {
                stroke = AppColor.text
            }

            chartVerticalGridLines {
                stroke = AppColor.text
            }
            axis {
                fill = AppColor.accent
            }

            chartPlotBackground {
                padding = box(2.px)
                backgroundRadius += box(16.0.px)
                backgroundColor += Color.TRANSPARENT
            }

            chartLegend {
                backgroundColor += AppColor.secondary
            }
        }

        linearGradient {
            backgroundColor  += LinearGradient(0.5,
                    0.0,
                    0.5,
                    1.0,
                    true,
                    CycleMethod.REFLECT,
                    Stop(0.0, AppColor.secondary),
                    Stop(1.0, AppColor.primary))

        }
        checkBox {
            fill = AppColor.secondary
        }

        roundedCorners {
            borderRadius += box(40.0.px)
        }

        button {
            backgroundRadius += box(16.px)
            backgroundColor += AppColor.accent
            textFill = AppColor.text
            padding = box(8.px, 16.px)
        }

        button and Stylesheet.pressed {
            borderRadius += box(16.px)
            borderColor += box(AppColor.text)
        }

        button and hover {
            cursor = Cursor.HAND
        }

        adaptiveWidth {
            button {
                maxWidth = Int.MAX_VALUE.px
            }
         }

        text {
            fill = AppColor.text
        }

        appText {
            fill = AppColor.text
        }

        slider {
            track {
                backgroundColor += AppColor.accent
            }
            thumb {
                backgroundColor += AppColor.secondary
            }
        }
    }
}