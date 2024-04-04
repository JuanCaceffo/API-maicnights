package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import java.time.LocalDateTime

data class CommentDTO(
    val imgPath: String,
    val title: String,
    val text: String,
    val rating: Double,
    val date: LocalDateTime
)

data class CommentCreateDTO(
    val ticketId: Long,
    val text: String,
    val rating: Double,
)


fun Comment.toUserDTO(): CommentDTO =
    CommentDTO("/mock-imgs/card-show-imgs/${show.showImg}", show.name, text, rating, date)
