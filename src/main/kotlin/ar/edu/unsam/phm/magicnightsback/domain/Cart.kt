package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Cart(
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.DETACH])
    val user: User
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val reservedTickets: MutableList<Ticket> = mutableListOf()

    fun getAllTickets() = reservedTickets

    fun reserveTicket(ticket: Ticket) {
        ticket.showDate.reserveSeat(ticket.seat, ticket.quantity)
        reservedTickets.add(ticket)
    }

    fun removeTickets(){
        reservedTickets.forEach { ticket -> ticket.showDate.releaseSeat(ticket.seat,ticket.quantity) }
        reservedTickets.clear()
    }

    fun ticketsSize() = reservedTickets.sumOf { ticket -> ticket.quantity }

    fun buyReservedTickets(){
        user.modifyBalance(-totalPrice())
        getAllTickets().forEach { ticket -> user.addTicket(ticket) }
        reservedTickets.clear()
    }

    fun totalPrice() = reservedTickets.sumOf { ticket -> ticket.price*-1 }
}