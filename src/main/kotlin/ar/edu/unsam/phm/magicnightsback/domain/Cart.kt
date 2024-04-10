package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable

class Cart(val user: User): Iterable() {
    val reservedTickets: MutableList<Ticket> = mutableListOf()

    fun getAllTickets() = reservedTickets

    fun reserveTicket(ticket: Ticket) {
        ticket.showDate.reserveSeat(ticket.seatType, ticket.quantity)
        reservedTickets.add(ticket)
    }

    fun removeTickets(){
        reservedTickets.forEach { ticket -> ticket.showDate.releaseSeat(ticket.seatType,ticket.quantity) }
        reservedTickets.clear()
    }

    fun ticketsSize() = reservedTickets.sumOf { ticket -> ticket.quantity }

    fun buyReservedTickets(){
        user.decreaseCredits(totalPrice())
        getAllTickets().forEach { ticket -> user.addTicket(ticket) }
        reservedTickets.clear()
    }

    fun totalPrice() = reservedTickets.sumOf { ticket -> ticket.price() }
}