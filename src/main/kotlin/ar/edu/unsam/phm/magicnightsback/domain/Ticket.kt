package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.*

@Entity
data class Ticket(
    @ManyToOne
    val show: Show,
    @ManyToOne
    val showDate: ShowDate,
    @ManyToOne
    val seat: Seat,
    @Column
    val seatPrice: Double,
    @Column
    val quantity: Int = 1,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0

    fun price() = seatPrice*quantity
}

