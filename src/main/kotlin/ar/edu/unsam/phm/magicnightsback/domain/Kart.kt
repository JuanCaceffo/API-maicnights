package ar.edu.unsam.phm.magicnightsback.domain

class Kart {
    private val tickets = mutableListOf<Ticket>()

    fun addTicket(ticket: Ticket){
        tickets.add(ticket)
        ticket.show.reserveSeat(ticket.seatType)
        ticket.status = TicketStatus.WISHLIST
    }

    fun removeTicket(ticket: Ticket){
        ticket.show.releaseSeat(ticket.seatType)
        tickets.remove(ticket)
    }

    fun removeAllTickets() {
        tickets.forEach {  it.show.releaseSeat(it.seatType) }
        tickets.clear()
    }

    fun getTickets(): List<Ticket>{
        return tickets
    }

    fun getTotalPrice(): Double {
        return 0.0
    }

    fun buy(user: User) {

        tickets.clear()
    }
}