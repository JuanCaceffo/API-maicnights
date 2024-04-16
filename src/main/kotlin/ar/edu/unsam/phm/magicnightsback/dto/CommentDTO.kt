package ar.edu.unsam.phm.magicnightsback.dto

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import java.time.LocalDateTime


data class CommentDTO(
    val id: Long = 0,
    var userId: Long = 0,
    var showId: Long = 0,
    var showImgSrc: String = "",
    var userImgSrc: String = "",
    var showName: String = "",
    var userName: String = "",
    val text: String = "",
    val rating: Double = 0.0,
    val date: LocalDateTime = LocalDateTime.of(0,-1,0,0,0,0)
)

private fun Comment.toDto(): CommentDTO = CommentDTO(
    id = this.id!!,
    text = this.text,
    rating = this.rating,
    date = this.date
)

fun Comment.toUserCommentDto(): CommentDTO = this.toDto().apply {
    userId = this@toUserCommentDto.user.id!!
    userImgSrc = this@toUserCommentDto.user.profileImgUrl
    userName = this@toUserCommentDto.user.name
}

fun Comment.toShowCommentDto(): CommentDTO = this.toDto().apply {
    showId = this@toShowCommentDto.show.id!!
    showImgSrc = this@toShowCommentDto.show.imgUrl
    showName = this@toShowCommentDto.show.name
}


//data class CommentCreateDTO(
//    val groupTicketId: Long,
//    val text: String,
//    val rating: Double,
//)

//fun Comment.toUserCommentDTO(): CommentDTO =
//    CommentDTO(show.showImg, show.name, text, rating, date)
