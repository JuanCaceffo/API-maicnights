package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.User


data class FriendDTO(
    val name: String,
    val surname: String,
    val img: String,
    val id: Long
)

fun User.toFriendDTO(): FriendDTO {
    return FriendDTO(
        this.firstName,
        this.username,
        this.profileImgUrl,
        this.id
    )
}
