package ar.edu.unsam.phm.magicnightsback.dto

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

private fun Comment.toDto(): CommentDTO = CommentDTO(
    id = this.id,
    text = this.text,
    rating = this.rating,
    date = this.date
)

fun Comment.toUserCommentDto(): CommentDTO = this.toDto().apply {
    userId = this@toUserCommentDto.user.id
    showId = this@toUserCommentDto.show.id
    imgSrc = this@toUserCommentDto.user.profileImgUrl
    name = this@toUserCommentDto.user.name
}

fun Comment.toShowCommentDto(): CommentDTO = this.toDto().apply {
    showId = this@toShowCommentDto.show.id
    imgSrc = this@toShowCommentDto.show.imgUrl
    name = this@toShowCommentDto.show.name
}


data class CommentCreateDTO(
    val userId: Long,
    val showId: Long,
    val showDateId: Long,
    val text: String,
    val rating: Double,
)