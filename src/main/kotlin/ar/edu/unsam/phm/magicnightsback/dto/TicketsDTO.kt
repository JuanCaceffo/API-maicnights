package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDateTime

data class TicketDTO(
    val ticketId: Long,
    val data: ShowData,
    val stats: ShowStats,
    val date: LocalDateTime,
    val price: Double,
    val quantity: Int,
    //val canBeCommented: Boolean? TODO: hablar con el profe sobre la validacion de el comentado de un show
)

fun Ticket.toTicketDTO(commentStats: CommentStadisticsDTO? = null,user: User, price: Double, quantity: Int) = TicketDTO(
    this.id,
    this.show.data(),
    this.show.stats(commentStats, user),
    this.showDate.date,
    price,
    quantity,
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