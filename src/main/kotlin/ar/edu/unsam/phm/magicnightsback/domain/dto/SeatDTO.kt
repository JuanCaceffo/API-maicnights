package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.Show

data class SeatDTO(
    val id: Long,
    val seatType: String,
    val price: Double,
    val available: Int,
)

fun Seat.toDTO(show: Show, available: Map<Seat, Int>) = SeatDTO(
    id,
    type.name,
    show.currentTicketPrice(this),
    available[this] ?: 0
)