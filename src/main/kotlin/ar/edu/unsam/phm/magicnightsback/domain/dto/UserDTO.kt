package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.domain.enums.UserRole
import java.time.LocalDate
import java.time.LocalDateTime

data class LoginUserDTO(
    var username: String = "",
    var password: String = "",
)

data class LoginUserResponseDTO(
    val id: Long,
    val role: UserRole
)


fun User.loginResponseDTO() = LoginUserResponseDTO(
    id,
    role
)

data class UserDTO(
    val id: Long,
    val profileImgUrl: String,
    val firstName: String,
    val lastName: String,
    val username: String,
    val birthday: LocalDate,
    val dni: Int,
    val role: UserRole,
    val balance: Double
)

fun User.toDTO(): UserDTO = UserDTO(
    id,
    profileImgUrl,
    firstName,
    lastName,
    username,
    birthday,
    dni,
    role,
    balance
)

data class UserUpdateDTO(
    val firstName: String,
    val lastName: String,
)

interface UserBalanceDTO {
    fun getId(): Long
    fun getAmount(): Double
    fun getTimeStamp(): LocalDateTime
}


