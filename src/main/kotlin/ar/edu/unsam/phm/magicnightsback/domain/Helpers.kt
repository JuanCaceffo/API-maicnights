package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import java.time.LocalDate
import java.time.Period

fun LocalDate.calculateAge(): Int = Period.between(this, LocalDate.now()).years

fun Number.throwErrorIfNegative(error: RuntimeException): Number {
    if (this.toFloat() < 0F) {
        throw error
    }
    return this
}

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}

fun Number.throwIfGreaterThan(number: Number,msg: String): Number {
    if (this.toFloat() > number.toFloat()){
        throw BusinessException(msg)
    }
    return this
}