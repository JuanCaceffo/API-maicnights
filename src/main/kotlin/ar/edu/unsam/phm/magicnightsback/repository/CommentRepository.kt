package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface CommentRepository : CrudRepository<Comment, Long>{
    fun findByShowId(id: String): Iterable<Comment>
    fun findByUserId(id: Long): Iterable<Comment>
    fun countByUserIdAndShowId(userId:Long, showId: String): Int

    @Query("""
        SELECT AVG(C.rating)
        FROM Comment C
        WHERE C.showId = :showId
    """)
    fun averageCommentRatingOfShow(@Param("showId") showId: String): Optional<Double>

    fun countAllByShowId(showId: String): Int
}