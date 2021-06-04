package com.example.app

import javafx.scene.Cursor
import javafx.scene.paint.*
import javafx.scene.text.FontWeight
import tornadofx.*
import java.awt.Paint

object CovidAppColor {
    private var primaryValue = "#33313b"
    private var secondaryyValue = "#3c4f65"
    private var accentValue = "#181810"
    val primary: Color = Color.web(primaryValue)
    val primaryTransparent: Color = Color.web("${primaryValue}30")
    val secondary: Color = Color.web(secondaryyValue)
    val accent: Color = Color.web(accentValue)
    val text: Color = Color.web("#efecec")
    val secondaryGradientStart: Color = accent.transparent(.15)
    val secondaryGradientEnd: Color = accent.transparent(.4)
}
fun Color.transparent(value: Double): Color {
    return Color(red, green, blue, value)
}




class CovidStyles : Stylesheet() {
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
            backgroundRadius += box(16.0.px)
            backgroundColor  += LinearGradient(0.5,
                        0.0,
                        0.5,
                        1.0,
                        true,
                        CycleMethod.REFLECT,
                        Stop(0.0, CovidAppColor.secondaryGradientStart),
                        Stop(1.0, CovidAppColor.secondaryGradientEnd)
            )
            chartTitle {
                textFill = CovidAppColor.text
            }

            axis {
                tickLabelFill = CovidAppColor.text
            }

            chartHorizontalGridLines {
                stroke = CovidAppColor.text
            }

            chartVerticalGridLines {
                stroke = CovidAppColor.text
            }
            axis {
                fill = CovidAppColor.accent
            }

            chartPlotBackground {
                padding = box(2.px)
                backgroundRadius += box(16.0.px)
                backgroundColor += Color.TRANSPARENT
            }

            chartLegend {
                backgroundColor += CovidAppColor.secondary
            }
        }

        linearGradient {
            backgroundColor  += LinearGradient(0.5,
                    0.0,
                    0.5,
                    1.0,
                    true,
                    CycleMethod.REFLECT,
                    Stop(0.0, CovidAppColor.secondary),
                    Stop(1.0, CovidAppColor.primary))

        }
        checkBox {
            fill = CovidAppColor.secondary
        }

        roundedCorners {
            borderRadius += box(40.0.px)
        }

        button {
            backgroundRadius += box(16.px)
            backgroundColor += CovidAppColor.accent
            textFill = CovidAppColor.text
            padding = box(8.px, 16.px)
        }

        button and Stylesheet.pressed {
            borderRadius += box(16.px)
            borderColor += box(CovidAppColor.text)
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
            fill = CovidAppColor.text
        }

        appText {
            fill = CovidAppColor.text
        }

        slider {
            track {
                backgroundColor += CovidAppColor.accent
            }
            thumb {
                backgroundColor += CovidAppColor.secondary
            }
        }
    }
}