package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.*
import java.time.LocalDateTime

data class PurchsedTicketDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val rating: Double?,
    val totalComments: Int?,
    val price: Double?,
    val dates: MutableList<LocalDateTime>,
    val userImageNames: List<String>,
)

fun Ticket.toPurchasedTicketDTO(userId: Long) = PurchsedTicketDTO(
    this.id,
    this.show.showImg,
    this.show.name,
    this.show.band.name,
    this.show.facility.name,
    this.show.totalRating(),
    this.show.comments().size,
    this.price,
    mutableListOf(this.showDate.date),
    this.show.friendsAttendeesProfileImages(userId)
)