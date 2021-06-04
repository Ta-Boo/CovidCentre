package projects.agentOrientedCovid.app.view

fun Double.roundUI(decimals: Int = 4): String = "%.${decimals}f".format(this).toDouble().toString()