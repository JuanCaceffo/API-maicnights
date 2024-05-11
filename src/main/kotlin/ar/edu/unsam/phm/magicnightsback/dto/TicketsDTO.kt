package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDateTime
import java.util.*

data class TicketDTO(
    val ticketId: Long,
    val data: ShowData,
    val showStats: ShowStats,
    val date: LocalDateTime,
    val price: Double,
    val quantity: Int,
    val canBeCommented: Boolean?
)

fun Ticket.toTicketDTO(commentStats: CommentStadisticsDTO? = null, user: User, price: Double, quantity: Int, canBeCommented: Boolean? = null) = TicketDTO(
    this.id,
    this.show.data(),
    this.show.stats(commentStats, user),
    this.showDate.date,
    price,
    quantity,
    canBeCommented
)

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
    val showDateId: UUID,
    val userId: UUID,
    val seat: SeatTypes
)

fun TicketRequestDTO.toModel() = Ticket(showDateId, userId, seat)