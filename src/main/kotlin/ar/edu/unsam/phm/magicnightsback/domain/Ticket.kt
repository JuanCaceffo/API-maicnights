package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDate

data class Ticket(
    val showId: Long,
    val date: LocalDate,
    val price: Double
) : Iterable() {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}