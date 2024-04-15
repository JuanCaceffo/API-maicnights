package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.ShowCommentError
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Comment(
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    val show: Show,
    @Column(length = 280)
    val text: String = "",
    val rating: Double = 0.0
) {
    @Id
    @GeneratedValue
    val id: Long? = null

    var date: LocalDateTime = LocalDateTime.now()

    constructor(user: User, show: Show, dto: CommentDTO) : this(
        user = user,
        show = show,
        text = dto.text,
        rating = dto.rating
    ) {
        require(rating in 0.0..5.0) { throw BusinessException(ShowCommentError.INVALID_RATTING) }
    }
}