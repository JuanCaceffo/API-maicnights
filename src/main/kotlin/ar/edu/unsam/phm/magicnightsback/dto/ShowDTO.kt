package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show

data class ShowDTO(
    val id: Long,
    val img: String,
    val name: String,
    val location: String,
    val rating: Int,
    val totalComments: Int,
    val prices: List<Double>
)

fun Show.toShowDTO() = ShowDTO(
    id = this.id,
    img = this.showImg,
    name = this.name,
    location = this.facility.name,
    rating = this.totalRating(),
    totalComments = this.comments.size,
    prices = this.allTicketPrices()
)