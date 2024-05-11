package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Ticket(
    val showDateId: Long,
    val userId: Long,

    @Column(length = ColumnLength.SMALL)
    val seat: SeatTypes
) {
    @Id
    val id: Long = 0

    var price: Double = 0.0

    @Column(nullable = true)
    var date: LocalDateTime? = null
}
