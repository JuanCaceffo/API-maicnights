package ar.edu.unsam.phm.magicnightsback.domain.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import java.time.LocalDateTime

data class CommentDTO(
    val id: Long,
    var userId: Long = 0,
    var showId: Long = 0,
    var imgSrc: String = "",
    var name: String = "",
    val text: String,
    val rating: Double,
    val date: LocalDateTime
)

data class CommentStadisticsDTO(
    val rating: Double = 0.0,
    val totalComments: Int = 0,
    val comments: List<CommentDTO> = listOf()
)

private fun Comment.toDTO(): CommentDTO = CommentDTO(
    id = this.id,
    text = this.text,
    rating = this.rating,
    date = this.date
)

fun Comment.toUserCommentDTO(): CommentDTO = this.toDTO().apply {
    userId = this@toUserCommentDTO.user.id
    showId = this@toUserCommentDTO.show.id
    //imgSrc = this@toUserCommentDto.show.imgUrl
    name = this@toUserCommentDTO.show.name
}

fun Comment.toShowCommentDTO(): CommentDTO = this.toDTO().apply {
    showId = this@toShowCommentDTO.show.id
    imgSrc = this@toShowCommentDTO.user.profileImgUrl
    name = this@toShowCommentDTO.user.firstName
}


data class CommentCreateDTO(
    val userId: Long,
    val showId: Long,
    val showDateId: Long,
    val text: String,
    val rating: Double,
)