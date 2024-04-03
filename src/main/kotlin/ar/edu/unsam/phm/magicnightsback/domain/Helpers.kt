package ar.edu.unsam.phm.magicnightsback.domain

import java.time.LocalDate
import java.time.Period

fun LocalDate.calculateAge(): Int = Period.between(this, LocalDate.now()).years

fun Int.throwErrorIfNegative(error: RuntimeException): Int {
    if (this < 0) {
        throw error
    }
    return this
}

object Comparar {
    fun total(buscado: String, listaComparar: List<String>, caseSense:Boolean = true) = listaComparar.any{ it.equals(buscado, ignoreCase = caseSense)}
    fun parcial(busquedoParcial: String, listaComparar: List<String>, caseSense:Boolean = true) = listaComparar.any { it.contains(busquedoParcial, ignoreCase = caseSense) }
}