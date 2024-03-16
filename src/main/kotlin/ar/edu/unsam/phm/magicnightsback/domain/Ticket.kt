package ar.edu.unsam.phm.magicnightsback.domain

import java.time.LocalDate

class Ticket(
    val number: Int,
    val quantity: Int,
    val seatType: SeatTypes,
    val date: LocalDate,
    val price: Double,
    val show: Show,
    var status: TicketStatus = TicketStatus.WISHLIST
)

enum class TicketStatus{
    WISHLIST,
    PURCHASED,
    PENDING
}



