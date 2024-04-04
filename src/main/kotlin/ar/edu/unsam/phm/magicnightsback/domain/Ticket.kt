package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable

data class Ticket(
    val show: Show,
    val showDate: ShowDate,
    val seatType: SeatTypes,
) : Iterable() {

    val price: Double = show.fullTicketPrice(seatType)
}