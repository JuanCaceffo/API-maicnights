package ar.edu.unsam.phm.magicnightsback.dto

data class PlaceDTO(
    val id: Long,
    val seatType: String,
    val price: Double,
    val reservedQuantity: Int,
)
