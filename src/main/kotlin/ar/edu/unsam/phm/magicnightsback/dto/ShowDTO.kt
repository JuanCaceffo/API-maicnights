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
    val price: Double,
    val prices: List<Double>,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
    val comments: List<CommentDTO>,
    val geolocation: String
)

data class ShowDateDetailsDTO (
    val showId: Long,
    val dateSeats: List<DateSeatsDTO>
)

data class DateSeatsDTO(
    val date: LocalDateTime,
    val seats: List<SeatsDTO>
)

data class SeatsDTO(
    val seatType: String,
    val price: Double,
    val maxToSell: Int,
)

data class CommentDTO(
    val userImg: String,
    val userName: String,
    val text: String,
    val rating: Double,
    val date: LocalDateTime
)

fun Show.toShowDTO(userId: Long, comments: List<CommentDTO> = emptyList(), price: Double = 0.0) =
    ShowDTO(
        this.id,
        this.showImg,
        this.name,
        this.band.name,
        this.facility.name,
        this.totalRating(),
        this.comments.size,
        price,
        this.allTicketPrices(),
        this.allDates(),
        this.friendsAttendeesProfileImages(userId),
        comments,
        this.facility.location.toString()
    )

fun Show.toShowDateDetailsDTO(dateSeats: List<DateSeatsDTO>) =
    ShowDateDetailsDTO(
        this.id,
        dateSeats
    )

