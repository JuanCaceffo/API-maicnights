package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.ShowCommentError
import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDateTime

class Comment(
    val show: Show,
    val text: String,
    val rating: Double
) : Iterable() {
    val date = LocalDateTime.now()
    init {
        require(rating in 0.0..5.0) { BusinessException(ShowCommentError.INVALID_RATTING) }
    }

    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}