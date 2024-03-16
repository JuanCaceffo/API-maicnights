package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.UserError

class Kart {
    private val tickets = mutableListOf<Ticket>()

    fun addTicket(ticket: Ticket) {
        ticket.show.reserveSeat(ticket.seatType, ticket.quantity)
        tickets.add(ticket)
    }

    fun removeTicket(ticket: Ticket) {
        ticket.show.releaseSeat(ticket.seatType, ticket.quantity)
        tickets.remove(ticket)
    }

    fun removeAllTickets() {
        tickets.forEach { removeTicket(it) }
    }

    fun getTickets(): List<Ticket> {
        return tickets
    }

    fun getTotalPrice(): Double {
        return 0.0
    }

    fun buy(user: User) {
        if (!validateEnoughCredit(user)) {
            throw BusinessException(UserError.MSG_NOT_ENOUGH_CREDIT)
        }
        user.pay(getTotalPrice())
        tickets.forEach {
            user.addTicket(it)
            it.show.addAttendee(user)
        }
        tickets.clear()
    }

    private fun validateEnoughCredit(user: User) = user.credit >= getTotalPrice()
}