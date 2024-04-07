package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable

data class Ticket(
    val show: Show,
    val showDate: ShowDate,
    val seatType: SeatTypes,
    val seatPrice: Double,
    val quantity: Int = 1,
) : Iterable() {

    fun price() = seatPrice*quantity

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}