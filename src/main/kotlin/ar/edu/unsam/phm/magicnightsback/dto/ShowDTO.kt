package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
import java.time.LocalDateTime

//import ar.edu.unsam.phm.magicnightsback.domain.User
//import org.uqbar.geodds.Point
//import java.time.LocalDateTime
//import kotlin.math.ceil

data class ShowDTO(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
    val prices: List<Double>?,
    val dates: List<LocalDateTime>
)

data class ShowUserDTO(
    val show: ShowDTO,
    val rating: Double,
    val totalComments: Int,
    val userImageNames: List<String>
)

data class ShowDetailsDTO(
    val show: ShowUserDTO,
    val comments: List<CommentDTO>,
    val geolocation: String,
)

data class ShowAdminDTO(
    val showDto: ShowDTO,
    val details: List<AdminSummaryDTO>
)

data class AdminSummaryDTO(
    val title: String,
    val description: String
)

fun Show.toShowDTO(dates: List<LocalDateTime>? = null) = ShowDTO(
    this.id,
    this.imgUrl,
    this.name,
    this.band.name,
    this.facility.name,
    this.allTicketPrices(),
    dates ?: this.allDates(),
)

fun Show.toShowUserDTO(commentStadistics: CommentStadisticsDTO,dates: List<LocalDateTime>? = null) =
    ShowUserDTO(
        this.toShowDTO(dates),
        commentStadistics.rating,
        commentStadistics.totalComments,
        listOf(),
    )

fun Show.toShowDetailsDTO(commentStadistics: CommentStadisticsDTO) = ShowDetailsDTO(
    this.toShowUserDTO(commentStadistics),
    commentStadistics.comments,
    this.geoLocationString()
)

fun Show.toShowAdminDTO(summary: List<AdminSummaryDTO>) = ShowAdminDTO(
    this.toShowDTO(),
    summary
)


//
//fun pointToDMS(point: Point): String {
//    val latitude = point.x
//    val longitude = point.y
//
//    val latitudeDirection = if (latitude >= 0) "N" else "S"
//    val longitudeDirection = if (longitude >= 0) "E" else "W"
//
//    return "Latitude: ${decimalToDMS(latitude)} $latitudeDirection, Longitude: ${decimalToDMS(longitude)} $longitudeDirection"
//}
//

//}
//
//data class SeatDTO(
//    val seatType: String,
//    val price: Double,
//    val maxToSell: Int,
//)
//
//
//fun Show.allCommentsDTO(): List<CommentDTO> {
//    return allAttendees().flatMap { user ->
//        user.comments.filter { it.show == this }.map {
//            CommentDTO(
//                user.profileImage,
//                user.username,
//                it.text,
//                it.rating,
//                it.date
//            )
//        }
//    }
//}
//
//fun Show.toShowDTO(user: User?, comments: List<CommentDTO> = emptyList(), price: Double = 0.0) =
//    ShowDTO(
//        this.id,
//        this.showImg,
//        this.name,
//        this.band.name,
//        this.facility.name,
//        this.totalRating(),
//        this.comments().size,
//        price,
//        this.allTicketPrices(),
//        this.allDates(),
//        if (user != null) this.friendsAttendeesProfileImages(user) else listOf(),
//        comments,
//        pointToDMS(this.facility.location),
//        listOf(
//            Details("Entradas vendidas totales: ", this.totalTicketsSold().toString())
//        ) +
//                this.getSeatTypes()
//                    .map { Details("Entradas Vendidas " + it.name, ticketsSoldOfSeatType(it).toString()) } +
//                listOf(
//                    Details("Recaudación total: ", this.totalSales().toString()),
//                    Details("Costo total: ", this.baseCost().toString()),
//                    Details("Personas en espera: ", this.pendingAttendees.size.toString())
//                )
//    )