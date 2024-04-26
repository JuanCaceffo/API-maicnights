package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.*

@Entity
data class Ticket(
    @ManyToOne(fetch = FetchType.LAZY)
    val show: Show,
    @ManyToOne(fetch = FetchType.LAZY)
    val showDate: ShowDate,
    @ManyToOne(fetch = FetchType.LAZY)
    val seat: Seat,
    @Column
    val quantity: Int = 1,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    val price = show.ticketPrice(seat)*quantity

}

