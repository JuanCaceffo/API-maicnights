package ar.edu.unsam.phm.magicnightsback.domain.factory

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.enums.TicketStatus
import org.springframework.stereotype.Component

enum class TicketFactoryTypes {
    NORMAL, CHEAP, EXPENSIVE
}

@Component
class Ticketfactory() {
    fun createTicket(type: TicketFactoryTypes, user: User, showDate: ShowDate, seat: Seat): Ticket = when (type) {
        TicketFactoryTypes.CHEAP -> CheapTicket(user, showDate, seat).build()
        TicketFactoryTypes.NORMAL -> NormalTicket(user, showDate, seat).build()
        TicketFactoryTypes.EXPENSIVE -> ExpensiveTicket(user, showDate, seat).build()
    }
}

class NormalTicket(
    override val user: User,
    override val showDate: ShowDate,
    override val seat: Seat

) : FactoryTickey {
    override fun build(): Ticket {
        val ticket = Ticket(
            user,
            showDate.id,
            showDate.show.id,
            seat
        )
        ticket.showDate = showDate
        ticket.apply {
            price = showDate.show.currentTicketPrice(seat)
            buyDate = showDate.date.minusDays(2)
            status = TicketStatus.BOUGHT
        }
        return ticket
    }

}

class CheapTicket(
    override val user: User,
    override val showDate: ShowDate,
    override val seat: Seat
) : FactoryTickey {
    override fun build(): Ticket {
        val ticket = Ticket(
            user,
            showDate.id,
            showDate.show.id,
            seat
        )
        ticket.showDate = showDate
        ticket.apply {
            price = showDate.show.currentTicketPrice(seat)
            buyDate = showDate.date.minusDays(1)
            status = TicketStatus.BOUGHT
        }
        return ticket
    }
}

class ExpensiveTicket(
    override val user: User,
    override val showDate: ShowDate,
    override val seat: Seat
) : FactoryTickey {
    override fun build(): Ticket {
        val ticket = Ticket(
            user,
            showDate.id,
            showDate.show.id,
            seat
        )
        ticket.showDate = showDate
        ticket.apply {
            price = showDate.show.currentTicketPrice(seat)
            buyDate = showDate.date.minusDays(4)
            status = TicketStatus.BOUGHT
        }
        return ticket
    }
}