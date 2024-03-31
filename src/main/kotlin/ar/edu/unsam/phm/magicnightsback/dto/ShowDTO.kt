package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show
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

fun Show.toShowDTO(friendsImageNames: List<String>) = ShowDTO(
    id = this.id,
    img = this.showImg,
    name = this.name,
    location = this.facility.name,
    rating = this.totalRating(),
    totalComments = this.comments.size,
    prices = this.allTicketPrices(),
    dates = this.allDates(),
    userImageNames = friendsImageNames
)
