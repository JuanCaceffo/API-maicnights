package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.User
import java.time.LocalDateTime

data class CommentUserDTO(
    val imgPath: String,
    val title: String,
    val text: String,
    val rating: Double,
    val dateT: LocalDateTime
)

data class CommentCreateDTO(
    val ticketId: Long,
    val text: String,
    val rating: Double,
)


fun Comment.toUserDTO(): CommentUserDTO =
    CommentUserDTO("/mock-imgs/card-show-imgs/${show.showImg}", show.name, text, rating, date)