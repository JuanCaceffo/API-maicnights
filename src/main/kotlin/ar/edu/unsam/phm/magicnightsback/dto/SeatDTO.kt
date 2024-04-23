package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Seat

data class SeatDTO(
    val seatType: String,
    val price: Double,
    val maxToSell: Int,
)
