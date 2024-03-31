package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Show

data class ShowDTO(
    val id: Long,
    val img: String,
    val name: String,
    val location: String,
//    val lowestPrice: Double,
//    val highestPrice: Double
    )

fun Show.toShowDTO() = ShowDTO(
    id = this.id,
    img = this.showImg,
    name = this.name,
    location = this.facility.name
//    lowestPrice = this.lowestPrice(),
//    highestPrice = this.highestPrice()
)