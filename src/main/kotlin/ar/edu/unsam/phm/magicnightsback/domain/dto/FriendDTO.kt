package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.User

data class FriendDTO(
    val id: Long,
    val firstName: String,
    val lastName: String,
    val profileImgUrl: String
)

fun User.toFriendDTO(): FriendDTO = FriendDTO(
    id,
    firstName,
    lastName,
    profileImgUrl
)
