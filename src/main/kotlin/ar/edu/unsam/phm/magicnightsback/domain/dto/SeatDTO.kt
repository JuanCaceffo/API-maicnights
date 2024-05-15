package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Seat

data class SeatDTO(
    val id: Long,
    val seatType: String,
    val price: Double,
    val available: Int,
)

fun Seat.toDTO() = SeatDTO(
    id,
    type.name,
    price = 0.0,
    available()
)