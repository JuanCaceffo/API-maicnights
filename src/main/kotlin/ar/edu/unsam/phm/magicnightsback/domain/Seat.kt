package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import jakarta.persistence.*

@Entity
data class Seat(
    val type: SeatTypes,
    val capacity: Int
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    val price = type.price
}