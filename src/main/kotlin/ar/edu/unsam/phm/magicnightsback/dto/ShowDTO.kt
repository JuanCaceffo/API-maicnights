package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
import java.time.LocalDateTime

data class ShowDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val rating: Double,
    val totalComments: Int,
    val prices: List<Double>,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
)

fun Show.toShowDTO(userId: Long) =
    ShowDTO(
        this.id,
        this.showImg,
        this.name,
        this.band.name,
        this.facility.name,
        this.totalRating(),
        this.comments.size,
        this.allTicketPrices(),
        this.allDates(),
        this.friendsAttendeesProfileImages(userId)
    )
data class CommentDTO(
    val userImg: String,
    val userName: String,
    val text: String,
    val rating: Double,
    val date: LocalDateTime
)