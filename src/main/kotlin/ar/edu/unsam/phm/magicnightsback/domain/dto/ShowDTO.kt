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