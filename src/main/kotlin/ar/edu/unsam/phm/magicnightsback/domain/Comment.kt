package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.CommentError
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
data class Comment(
    @ManyToOne(fetch = FetchType.LAZY)
    val user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    val show: Show,
    @Column(length = 400)
    var text: String = "",
    val rating: Double = 0.0
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var date: LocalDateTime = LocalDateTime.now()

    constructor(user: User, show: Show, dto: CommentDTO) : this(
        user = user,
        show = show,
        text = dto.text,
        rating = dto.rating
    ) {
        id = dto.id
        require(rating in 0.0..5.0) { throw BusinessException(CommentError.INVALID_RATTING) }
    }

    fun canBeDeletedBy( userId: Long) = user.id == userId

    init {
        if (text.length > 400) text =  text.take(400)
    }
}