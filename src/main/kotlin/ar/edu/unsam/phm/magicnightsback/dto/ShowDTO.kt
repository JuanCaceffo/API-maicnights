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

fun Show.toShowDTO() =
    ShowDTO(
        this.id,
        this.showImg,
        this.name,
        this.facility.name,
        this.totalRating(),
        this.comments.size,
        this.allTicketPrices()
    )
