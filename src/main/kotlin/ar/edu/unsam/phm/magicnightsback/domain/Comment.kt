package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.ShowCommentError
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import java.time.LocalDateTime

@Entity
class Comment {
    @Id
    @GeneratedValue
    val id: Long? = null

    @Column
    var date: LocalDateTime = LocalDateTime.now()

    @Column(length = 280)
    val text: String = ""

    @Column
    val rating: Double = 0.0

//    @Column
//    val user: User? = null

    init {
        require(rating in 0.0..5.0) { BusinessException(ShowCommentError.INVALID_RATTING) }
    }
}