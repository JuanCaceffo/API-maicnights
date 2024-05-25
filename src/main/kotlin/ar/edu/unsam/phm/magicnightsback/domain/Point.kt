package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import kotlin.math.ceil

@Embeddable
data class Point(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {
    fun toGeolocation(): String {
        val latitudeDirection = if (latitude >= 0) "N" else "S"
        val longitudeDirection = if (longitude >= 0) "E" else "W"

        return "Latitude: ${decimalToDMS(latitude)} $latitudeDirection, Longitude: ${decimalToDMS(longitude)} $longitudeDirection"
    }
    private fun decimalToDMS(decimal: Double): String {
        val degrees = decimal.toInt()
        val minutesDouble = (decimal - degrees) * 60
        val minutes = minutesDouble.toInt()
        val secondsDouble = (minutesDouble - minutes) * 60
        val seconds = ceil(secondsDouble).toInt()

        return "$degrees° $minutes' $seconds''"
    }

    init {
        require(latitude in -90.0..90.0) { "Invalid latitude value" }
        require(longitude in -180.0..180.0) { "Invalid longitude value" }
    }
}