package ar.edu.unsam.phm.magicnightsback.domain.enums

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

enum class SeatTypes(val price: Double) {
    UPPERLEVEL(10000.0),
    FIELD(15000.0),
    BOX(20000.0),
    LOWERLEVEL(10000.0),
    PULLMAN(15000.0)
}