package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
import org.uqbar.geodds.Point
import java.time.LocalDateTime
import kotlin.math.ceil

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

fun pointToDMS(point: Point): String {
    val latitude = point.x
    val longitude = point.y

    val latitudeDirection = if (latitude >= 0) "N" else "S"
    val longitudeDirection = if (longitude >= 0) "E" else "W"

    return "Latitude: ${decimalToDMS(latitude)} $latitudeDirection, Longitude: ${decimalToDMS(longitude)} $longitudeDirection"
}

fun decimalToDMS(decimal: Double): String {
    val degrees = decimal.toInt()
    val minutesDouble = (decimal - degrees) * 60
    val minutes = minutesDouble.toInt()
    val secondsDouble = (minutesDouble - minutes) * 60
    val seconds = ceil(secondsDouble).toInt()

    return "$degrees° $minutes' $seconds''"
}
data class SeatDTO(
    val seatType: String,
    val price: Double,
    val maxToSell: Int,
)

data class CommentDTO(
    val userImg: String,
    val name: String,
    val text: String,
    val rating: Double,
    val date: LocalDateTime
)

fun Show.allCommentsDTO(): List<CommentDTO> {
    return allAttendees().flatMap {user ->
        user.comments.filter{ it.show == this }.map {
            CommentDTO(
                user.profileImage,
                user.username,
                it.text,
                it.rating,
                it.date
            )
        }
    }
}

fun Show.toShowDTO(userId: Long, comments: List<CommentDTO> = emptyList(), price: Double = 0.0) =
    ShowDTO(
        this.id,
        this.showImg,
        this.name,
        this.band.name,
        this.facility.name,
        this.totalRating(),
        this.comments().size,
        price,
        this.allTicketPrices(),
        this.allDates(),
        this.friendsAttendeesProfileImages(userId),
        comments,
        pointToDMS(this.facility.location)
    )