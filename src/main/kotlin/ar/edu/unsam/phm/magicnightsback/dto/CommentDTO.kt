package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import java.time.LocalDateTime

data class CommentDTO(
    val userId: Long,
    val showId: Long,
    val imgSrc: String,
    val fromTo: String,
    val text: String,
    val rating: Double,
    val date: LocalDateTime
)

data class CommentCreateDTO(
    val groupTicketId: Long,
    val text: String,
    val rating: Double,
)

//fun Comment.toUserCommentDTO(): CommentDTO =
//    CommentDTO(show.showImg, show.name, text, rating, date)
