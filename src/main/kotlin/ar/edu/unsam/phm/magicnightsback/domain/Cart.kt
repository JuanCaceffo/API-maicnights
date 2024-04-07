package ar.edu.unsam.phm.magicnightsback.domain

class Cart {
    val userCarts: MutableMap<User, MutableList<Ticket>> = mutableMapOf()

    private fun getOrAddCartTo(user:User) = userCarts.getOrPut(user) { mutableListOf() }


    fun getAllTickets(user: User) = getOrAddCartTo(user)

    fun reserveTicket(user: User, ticket: Ticket) {
        ticket.showDate.reserveSeat(ticket.seatType, ticket.quantity)
        getOrAddCartTo(user).add(ticket)
    }

    fun removeTickets(user: User){
        getOrAddCartTo(user).forEach { ticket -> ticket.showDate.releaseSeat(ticket.seatType,ticket.quantity) }
        getOrAddCartTo(user).clear()
    }

    fun ticketsSize(user: User) = getOrAddCartTo(user).size
}