package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.*

//@Entity
//data class Ticket(
//    @ManyToOne(fetch = FetchType.LAZY)
//    val show: Show,
//    @ManyToOne(fetch = FetchType.LAZY)
//    val showDate: ShowDate,
//    @Enumerated(EnumType.STRING)
//    val seat: SeatTypes,
//    @Column
//    val quantity: Int = 1,
//) {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    var id: Long = 0
//    val price = show.ticketPrice(seat) * quantity
//
//}

@Entity
data class Ticket(
    val showDateId: UUID,
    val userId: UUID,

    @Column(length = ColumnLength.SMALL)
    val seat: SeatTypes
) {
    @Id
    val id: UUID = UUID.randomUUID()

    var price: Double = 0.0

    @Column(nullable = true)
    var date: LocalDateTime? = null

}
