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
    val latitude = point.getX()
    val longitude = point.getY()

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
        pointToDMS(this.facility.location)
    )

fun Show.toShowDateDetailsDTO(dateSeats: List<DateSeatsDTO>) =
    ShowDateDetailsDTO(
        this.id,
        dateSeats
    )

