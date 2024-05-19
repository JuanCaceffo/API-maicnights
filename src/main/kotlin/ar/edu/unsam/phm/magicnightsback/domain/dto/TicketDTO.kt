package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.enums.SeatTypes
import java.time.LocalDateTime


data class TicketDTO(
    val id: Long,
    val seat: SeatDTO,
    val showDate: ShowDate
)

fun Ticket.toDTO(): TicketDTO = TicketDTO(
    id,
    seat.toDTO(this.showDate.show),
    showDate
)



//data class TicketDTO(
//    val ticketId: Long,
//    val data: ShowData,
//    val showStats: ShowStats,
//    val date: ShowDateDTO,
//    val price: Double,
//    val quantity: Int,
//    val canBeCommented: Boolean?
//)

//fun Ticket.toTicketDTO(commentStats: CommentStadisticsDTO? = null, user: User, price: Double, quantity: Int, canBeCommented: Boolean? = null) = TicketDTO(
//    this.id,
//    this.show.data(),
//    this.show.stats(commentStats, user),
//    this.showDate.toShowDateDTO(),
//    price,
//    quantity,
//    canBeCommented
//)

data class TicketCreateDTO(
    val showId: Long,
    val showDateId: Long,
    val seatTypeName: SeatTypes,
    val quantity: Int,
)

interface TicketResult {
    fun getQuantity(): Int
    fun getTicketId(): Long
    fun getShowDateId(): Long
    fun getShowId(): Long?
    fun getSeat(): String
}

data class TicketRequestDTO(
    val showDateId: Long,
    val seatId: Long,
    val quantity: Int
)

data class TicketResponseDTO(
    val id: Long,
    val date: LocalDateTime?,
    val price: Double
)

//fun Ticket.toResponseDTO(): TicketResponseDTO = TicketResponseDTO(
//    this.id,
//    this.date,
//    this.price
//)