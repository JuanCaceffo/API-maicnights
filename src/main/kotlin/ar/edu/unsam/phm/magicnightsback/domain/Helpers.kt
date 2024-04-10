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

fun Double.truncar(): Double {
    return String.format("%.2f", this).toDouble()
}

object Comparar {
    fun total(buscado: String, listaComparar: List<String>, caseSense: Boolean = true) =
        listaComparar.any { it.equals(buscado, ignoreCase = caseSense) }

    fun parcial(busquedoParcial: String, listaComparar: List<String>, caseSense: Boolean = true) =
        listaComparar.any { it.contains(busquedoParcial, ignoreCase = caseSense) }
}

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+".toRegex(), "")
}

fun Number.throwIfGreaterThan(number: Number, msg: String): Number {
    if (this.toFloat() > number.toFloat()) {
        throw BusinessException(msg)
    }
    return this
}