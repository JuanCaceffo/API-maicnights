package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import java.time.LocalDateTime

data class PurchsedTicketDTO(
    val id: Long,
    val showImg: String,
    val name: String,
    val ubication: String,
    val valoration: Double,
    val valorationSize: Int,
    val price: Double,
    val dates: MutableList<LocalDateTime>,
    val userImgs: List<String>,
)

fun Ticket.toPurchasedTicketDTO(userId: Long) = PurchsedTicketDTO(
    this.id,
    this.show.showImg,
    this.show.name,
    this.show.facility.name,
    this.show.totalRating(),
    this.show.comments.size,
    this.price,
    mutableListOf(this.showDate.date),
    this.show.friendsAttendeesProfileImages(userId)
)