package util

import kotlin.math.roundToInt

fun Float.roundStringToDecimals(decimals: Int): String {
    var dotAt = 1
    repeat(decimals) { dotAt *= 10 }
    val roundedValue = (this * dotAt).roundToInt()
    val integral = (roundedValue / dotAt)
    val fractional = ((roundedValue % dotAt).toFloat() / dotAt).toString().let {
        if(it.split(".").last().length == 1) "${it}0" else it
    }
    return "$integral.${fractional.split(".").last()}"
}