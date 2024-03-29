package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import ar.edu.unsam.phm.magicnightsback.serializers.View
import com.fasterxml.jackson.annotation.JsonView
import java.time.LocalDate

data class Ticket (
    val showId: Long,
    val date: LocalDate,
    @JsonView(View.Iterable.Show.Purchased::class)
    val price: Double
) : Iterable() {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}