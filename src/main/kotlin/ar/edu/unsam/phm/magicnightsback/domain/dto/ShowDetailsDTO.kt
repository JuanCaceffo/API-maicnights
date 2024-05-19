package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Point
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.interfaces.*

data class ShowDetailsDTO(
    override val id: Long,
    override val showImg: String,
    override val showName: String,
    override val bandName: String,
    override val facilityName: String,
    override val dates: List<ShowDateDTO>,
    override val prices: List<Double>,
    override val rating: Double,
    override val totalComments: Int,
    override val geolocation: String,
    override val comments: Set<CommentDTO>
) : ShowData, ShowStats, ShowDetails, ShowDates

data class ShowDetailsExtraDataDTO(
    override val rating: Double,
    override val totalComments: Int,
    val dates: List<ShowDateDTO>,
    val comments: Set<CommentDTO>
): ShowStats

fun Show.toShowDetailsDTO(stats: ShowDetailsExtraDataDTO) = ShowDetailsDTO(
    id,
    imgUrl,
    name,
    band.name,
    facility.name,
    stats.dates,
    allTicketPrices(),
    stats.rating,
    stats.totalComments,
    facility.location.toGeolocation(),
    stats.comments
)