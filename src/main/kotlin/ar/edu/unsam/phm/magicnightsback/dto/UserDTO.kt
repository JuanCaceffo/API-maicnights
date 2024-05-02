package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.BalanceHistory
import ar.edu.unsam.phm.magicnightsback.domain.User
import java.time.LocalDate
import java.time.LocalDateTime

data class LoginUserDTO(
    var username: String = "",
    var password: String = "",
)

data class UserDTO(
    val id: Long,
    val profileImg: String,
    val name: String,
    val surname: String,
    val username: String,
    val birthday: LocalDate,
    val dni: Int,
)

data class UserUpdateDTO(
    val name: String,
    val surname: String,
)

interface UserBalanceDTO {
    fun getId(): Long
    fun getAmount(): Double
    fun getTimeStamp(): LocalDateTime
}

fun User.toDTO(): UserDTO = UserDTO(
    this.id,
    this.profileImgUrl,
    this.name,
    this.surname,
    this.username,
    this.birthday,
    this.dni
)
