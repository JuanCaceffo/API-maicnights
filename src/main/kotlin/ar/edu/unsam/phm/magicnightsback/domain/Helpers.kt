package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.InternalServerError
import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.RepositoryError
//import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.math.floor
import kotlin.math.pow

fun LocalDate.calculateAge(): Int = Period.between(this, LocalDate.now()).years

fun List<Double>.averageOrZero() = if (isEmpty()) 0.0 else average()

fun Number.throwErrorIfNegative(error: RuntimeException): Number {
    if (this.toFloat() < 0F) {
        throw error
    }
    return this
}

fun Double.truncate(): Double {
    val factor = 10.0.pow(2.0)
    return floor(this * factor) / factor
}

object Comparar {
    fun total(buscado: String, listaComparar: List<String>, caseSense: Boolean = true) =
        listaComparar.any { it.equals(buscado, ignoreCase = caseSense) }

    fun parcial(busquedoParcial: String, listaComparar: List<String>, caseSense: Boolean = true) =
        listaComparar.any { it.contains(busquedoParcial, ignoreCase = caseSense) }
}

fun String.removeSpaces(): String {
    return this.trim().replace("\\s+|/".toRegex(), "")
}

fun Number.throwIfGreaterThan(number: Number, msg: String): Number {
    if (this.toFloat() > number.toFloat()) {
        throw BusinessException(msg)
    }
    return this
}

fun <T> validateOptionalIsNotNull(optional: Optional<T>, msg: String? = null):T{
    if(optional.isEmpty) {
        throw NotFoundException(msg ?: RepositoryError.ELEMENT_NOT_FOUND)}
    return optional.get()
}

//fun parseLocalDate(dateString: String): LocalDate {
//    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//    return LocalDate.parse(dateString, formatter)
//}

fun parseLocalDateTime(dateString: String): LocalDateTime {
    val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
    return LocalDateTime.parse(dateString, formatter)
}

