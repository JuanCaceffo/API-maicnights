package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne

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
    var id: Long = 0

    fun price() = seatPrice*quantity
}

