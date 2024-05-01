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
    val showStats: ShowStats,
    val prices: List<Double>? = null,
    val dates: List<ShowDateDTO>? = null,
    val comments: List<CommentDTO>? = null,
    val geolocation: String? = null,
    val price: Double? = null,
)

data class AdminSummaryDTO(
    val title: String, val description: String
)

data class ShowStats(
    val rating: Double? = null,
    val totalComments: Int? = null,
    val userImageUrls: List<String>? = null,
)

fun Show.data() = ShowData(
    this.id,
    this.imgUrl,
    this.name,
    this.band.name,
    this.facility.name,
)

fun Show.stats(commentStats: CommentStadisticsDTO? = null,  user: User? = null) =
    ShowStats(
        commentStats?.rating,
        commentStats?.totalComments,
        if (user != null) this.friendsAttendeesProfileImages(user) else null,
    )

fun Show.toShowDTO(commentStats: CommentStadisticsDTO? = null, user: User? = null) = ShowDTO(
    this.data(),
    this.stats(commentStats,user),
    this.allTicketPrices(),
    this.allDatesWithIds(),
)

fun Show.toShowDetailsDTO(commentStats: CommentStadisticsDTO? = null) = ShowDTO(
    this.data(),
    this.stats(commentStats),
    null,
    this.allDatesWithIds(),
    null,
    this.geoLocationString()
)