package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.data.constants.ColumnLength
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.enums.TicketStatus
import jakarta.persistence.*
import java.time.LocalDateTime

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
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,

    @ManyToOne
    val showDate: ShowDate,

    @ManyToOne(fetch = FetchType.EAGER)
    val seat: Seat
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var price: Double = 0.0

    @Enumerated(EnumType.STRING)
    var status: TicketStatus = TicketStatus.AVAILABLE

    @Column(nullable = true)
    var buyDate: LocalDateTime? = null
}
