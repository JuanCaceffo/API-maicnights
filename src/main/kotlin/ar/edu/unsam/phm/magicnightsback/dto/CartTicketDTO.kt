package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDateTime

data class TicketCartDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val rating: Double,
    val totalComments: Int,
    val price: Double,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
    val quantity: Int
)

fun Ticket.toCartDTO(userId: Long, showDates: List<LocalDateTime>, price: Double, quantity: Int) = TicketCartDTO(
    this.id,
    this.show.showImg,
    this.show.name,
    this.show.band.name,
    this.show.facility.name,
    this.show.totalRating(),
    this.show.comments().size,
    price,
    showDates,
    this.show.friendsAttendeesProfileImages(userId),
    quantity
)

data class TicketCreateDTO(
    val showId: Long,
    val showDateId: Long,
    val price: Double,
    val seatTypeName: AllSetTypeNames,
    val quantity: Int
)