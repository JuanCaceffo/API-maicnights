package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.User
import java.time.LocalDate
import java.time.LocalDateTime

data class ShowDTO(
    val id: Long,
    val img: String,
    val name: String,
    val location: String,
    val rating: Double,
    val totalComments: Int,
    val prices: List<Double>,
    val dates: List<LocalDateTime>,
    val userImageNames: List<String>,
)

data class ShowDetailsDTO (
    val id: Long,
    val showImg: String,
    val bandImg: String,
    val bandName: String,
    val facilityName: String,
    val geolocation: String,
    val rating: Double,
    val comments: List<CommentDTO>,
    val dates: List<LocalDateTime>,
    val seats: List<SeatDTO>
)

data class SeatDTO(
    val seatType: String,
    val price: Double,
    val maxToSell: Int
)

data class CommentDTO(
    val userImg: String,
    val userName: String,
    val bandImg: String,
    val bandName: String,
    val text: String,
    val rating: Double
)

data class DateDTO(
    val date: LocalDateTime,
    val soldout: Boolean
)

fun Show.toShowDTO(userId: Long) =
    ShowDTO(
        this.id,
        this.showImg,
        this.name,
        this.facility.name,
        this.totalRating(),
        this.comments.size,
        this.allTicketPrices(),
        this.allDates(),
        this.friendsAttendeesProfileImages(userId)
    )

fun Show.toShowDetailsDTO(seats: List<SeatDTO>, comments: List<CommentDTO>) =
    ShowDetailsDTO(
        this.id,
        this.showImg,
        this.name,
        this.band.name,
        this.facility.name,
        this.facility.location.toString(),
        this.totalRating(),
        comments,
        this.allDates(),
        seats,
    )

