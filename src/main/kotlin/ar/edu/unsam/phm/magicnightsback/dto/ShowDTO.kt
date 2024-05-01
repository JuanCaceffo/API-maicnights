package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.User

data class ShowData(
    val id: Long,
    val showImg: String,
    val showName: String,
    val bandName: String,
    val facilityName: String,
)

data class ShowDTO(
    val data: ShowData,
    val prices: List<Double>? = null,
    val dates: List<ShowDateDTO>? = null,
    val rating: Double? = null,
    val totalComments: Int? = null,
    val userImageNames: List<String>? = null,
    val comments: List<CommentDTO>? = null,
    val geolocation: String? = null,
    val price: Double? = null,
)

data class AdminSummaryDTO(
    val title: String, val description: String
)

fun Show.data() = ShowData(
    this.id,
    this.imgUrl,
    this.name,
    this.band.name,
    this.facility.name,
)

fun Show.toShowDTO(commentStats: CommentStadisticsDTO? = null, user: User? = null) = ShowDTO(
    this.data(),
    this.allTicketPrices(),
    this.allDatesWithIds(),
    commentStats?.rating,
    commentStats?.totalComments,
    if (user != null) this.friendsAttendeesProfileImages(user) else null,
)

fun Show.toShowDetailsDTO(commentStats: CommentStadisticsDTO? = null) = ShowDTO(
    this.data(),
    null,
    this.allDatesWithIds(),
    commentStats?.rating,
    commentStats?.totalComments,
    null,
    commentStats?.comments,
    this.geoLocationString()
)