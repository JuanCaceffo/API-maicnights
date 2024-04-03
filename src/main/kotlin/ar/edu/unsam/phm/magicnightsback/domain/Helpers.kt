package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import java.time.LocalDate
import java.time.Period

fun LocalDate.calculateAge(): Int = Period.between(this, LocalDate.now()).years

fun Int.throwErrorIfNegative(error: RuntimeException): Int {
    if (this < 0) {
        throw error
    }
    return this
}

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}

fun Int.throwIfGreaterThan(number: Int,msg: String): Int {
    if (this > number){
        throw BusinessException(msg)
    }
    return this
}