package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.User
import java.time.LocalDate

data class UserDTO(
    val id: Long,
    val profileImg: String,
    val name: String,
    val surname: String,
    val username: String,
    val birthday: LocalDate,
    val dni: Int,
)

fun User.toDTO(): UserDTO {
    return UserDTO(
        this.id,
        this.profileImage,
        this.name,
        this.surname,
        this.username,
        this.birthday,
        this.dni
    )
}