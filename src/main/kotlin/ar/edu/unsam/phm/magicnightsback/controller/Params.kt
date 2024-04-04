package ar.edu.unsam.phm.magicnightsback.controller
data class BaseFilterParams(
    val userId: Long,
    val bandKeyword: String,
    val facilityKeyword: String,
    val withFriends: Boolean
)
