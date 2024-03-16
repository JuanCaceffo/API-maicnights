package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import java.time.LocalDate

abstract class TicketProps(
    val number: Int,
    val show: Concert,
    val seatType: SeatTypes
) : RepositoryProps()

class Ticket(
    number: Int,
    show: Concert,
    seatType: SeatTypes,
    val quantity: Int,
    val date: LocalDate,
    val price: Double
) : TicketProps(number, show, seatType) {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}

class PendingTicket(
    number: Int,
    show: Concert,
    seatType: SeatTypes
) : TicketProps(number, show, seatType) {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}