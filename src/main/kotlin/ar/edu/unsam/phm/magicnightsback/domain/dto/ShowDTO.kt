package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.interfaces.*

data class ShowDTO(
    override val id: String,
    override val showImg: String,
    override val showName: String,
    override val bandName: String,
    override val facilityName: String,
    override val dates: List<ShowDateDTO>,
    override val prices: List<Double>,
    override val rating: Double = 0.0,
    override val totalComments: Int = 0,
    override val friendsImgs: List<String> = listOf(),
    override val totalFriendsAttending: Int = 0,
) : ShowData, ShowStats, ShowWithFriends, ShowDates

data class ShowExtraDataDTO(
    override val friendsImgs: List<String>,
    override val totalFriendsAttending: Int,
    override val rating: Double,
    override val totalComments: Int,
    val dates: List<ShowDateDTO>
): ShowStats, ShowWithFriends

data class ShowAdminDTO(
    override val id: String,
    override val showImg: String,
    override val showName: String,
    override val bandName: String,
    override val facilityName: String,
    override val dates: List<ShowDateDTO>,
    override val prices: List<Double>
) : ShowData, ShowDates

fun Show.toDTO(stats:ShowExtraDataDTO) = ShowDTO(
    id,
    imgUrl(),
    name,
    band.name,
    facility.name,
    stats.dates,
    allTicketPrices(),
    stats.rating,
    stats.totalComments,
    stats.friendsImgs,
    stats.totalFriendsAttending
)

//data class ShowData(
//    val id: Long,
//    val showImg: String,
//    val showName: String,
//    val bandName: String,
//    val facilityName: String,
//)
//
//data class ShowStats(
//    val rating: Double? = null,
//    val totalComments: Int? = null,
//    val userImageNames: List<String>? = null,
//)
//
//data class ShowDTO(
//    val data: ShowData,
//    val showStats: ShowStats,
//    val prices: List<Double>? = null,
//    val dates: List<ShowDateDTO>? = null,
//    val comments: List<CommentDTO>? = null,
//    val geolocation: String? = null,
//    val price: Double? = null,
//)
//
//data class AdminSummaryDTO(
//    val title: String, val description: String
//)
//
//fun Show.data() = ShowData(
//    this.id,
//    this.imgUrl,
//    this.name,
//    this.band.name,
//    this.facility.name,
//)
//
//fun Show.stats(commentStats: CommentStadisticsDTO? = null,  user: User? = null) =
//    ShowStats(
//        commentStats?.rating,
//        commentStats?.totalComments,
//        if (user != null) this.friendsAttendeesProfileImages(user) else null,
//    )
//
//fun Show.toShowDTO(commentStats: CommentStadisticsDTO? = null, user: User? = null) = ShowDTO(
//    this.data(),
//    this.stats(commentStats,user),
//    this.allTicketPrices(),
//    this.allDatesWithIds(),
//)
//
//fun Show.toShowDetailsDTO(commentStats: CommentStadisticsDTO? = null) = ShowDTO(
//    this.data(),
//    this.stats(commentStats),
//    null,
//    this.allDatesWithIds(),
//    null,
//    this.geoLocationString()
//)