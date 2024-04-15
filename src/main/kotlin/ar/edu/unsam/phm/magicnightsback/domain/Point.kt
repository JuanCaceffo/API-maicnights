package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.Embeddable
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Embeddable
data class Point(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) {

    init {
        require(latitude in -90.0..90.0) { "Invalid latitude value" }
        require(longitude in -180.0..180.0) { "Invalid longitude value" }
    }
}