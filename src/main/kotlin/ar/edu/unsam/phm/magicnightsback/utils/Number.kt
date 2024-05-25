package ar.edu.unsam.phm.magicnightsback.utils

import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import kotlin.math.pow
import kotlin.math.round

fun Number.notNegative(error:RuntimeException): Number {
    if (this.toFloat() < 0F) {
        throw error
    }
    return this
}

fun Double.truncate(decimals:Int = 0): Double {
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}

fun List<Double>.averageOrZero() = if (isEmpty()) 0.0 else average()

fun Number.throwIfGreaterThan(number: Number, msg: String): Number {
    if (this.toFloat() > number.toFloat()) {
        throw BusinessException(msg)
    }
    return this
}