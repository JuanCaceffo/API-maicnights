package ar.edu.unsam.phm.magicnightsback.utils

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.Period
import java.time.format.DateTimeFormatter

fun fromNow(minutes: Int) = System.currentTimeMillis() + minutes * 60 * 1000

fun LocalDate.calculateAge(): Int = Period.between(this, LocalDate.now()).years

fun parseLocalDateTime(dateString: String): LocalDateTime {
    return LocalDateTime.parse(dateString, formatter())
}

fun parseLocalDate(dateString: String): LocalDate {
    return LocalDate.parse(dateString, formatter())
}

private fun formatter(): DateTimeFormatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME