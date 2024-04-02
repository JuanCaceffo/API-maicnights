package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import java.time.LocalDateTime

data class TicketCartDTO(
    val id: Long,
    val img: String,
    val name: String,
    val location: String,
    val rating: Double,
    val totalComments: Int,
    val price: Double,
    val date: LocalDateTime,
    val userImageNames: List<String>,
)

fun Ticket.toCartDTO(userId: Long) = TicketCartDTO(
    this.id,
    this.show.showImg,
    this.show.name,
    this.show.facility.name,
    this.show.totalRating(),
    this.show.comments.size,
    this.price,
    this.showDate.date,
    this.show.friendsAttendeesProfileImages(userId)
)