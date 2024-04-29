package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDate

data class TicketDTO(
    val showUserDTO: ShowUserDTO,
    val price: Double,
    val quantity: Int,
    //val canBeCommented: Boolean? TODO: hablar con el profe sobre la validacion de el comentado de un show
)

fun Ticket.toTicketDTO(commentStadistics: CommentStadisticsDTO,user: User, price: Double, quantity: Int) = TicketDTO(
    this.show.toShowUserDTO(commentStadistics, user, listOf(this.showDate.date)),
    price,
    quantity,
)

data class TicketCreateDTO(
    val showId: Long,
    val showDateId: Long,
    val seatTypeName: SeatTypes,
    val quantity: Int
)