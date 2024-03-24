package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import java.time.LocalDate

abstract class TicketProps(
    val number: Int,
    val show: Show,
    val seatType: SeatTypes
) : RepositoryProps()

class Ticket(
    number: Int,
    show: Show,
    val date: ShowDate,
    seatType: SeatTypes,
    val quantity: Int,
    val price: Double
) : TicketProps(number, show, seatType) {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class PendingTicket(
    number: Int,
    show: Show,
    seatType: SeatTypes
) : TicketProps(number, show, seatType) {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}