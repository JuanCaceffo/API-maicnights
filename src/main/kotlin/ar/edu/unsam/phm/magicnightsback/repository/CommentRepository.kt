package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface CommentRepository : CrudRepository<Comment, Long>{
    fun findByShowId(id: Long): List<Comment>
    fun findByUserId(id: Long): List<Comment>
    fun countByUserIdAndShowId(userId:Long, showId:Long): Int

    @Query("""
        SELECT AVG(C.rating)
        FROM Comment C
        WHERE C.show.id = :showId
    """)
    fun averageCommentRatingOfShow(@Param("showId") showId:Long): Optional<Double>

    fun countAllByShowId(showId:Long): Int
}