package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import java.time.LocalDateTime

data class CommentUserDTO(
    val imgPath: String,
    val title: String,
    val text: String,
    val rating: Double,
    val dateT: LocalDateTime
)

fun Comment.toUserDTO(): CommentDTO = CommentDTO(show.showImg, show.name, text, rating, date)