package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import org.springframework.data.repository.CrudRepository

interface CommentRepository : CrudRepository<Comment, Long>{
    fun getShowComments(id: Long): List<Comment>
    fun getUserComments(id: Long): List<Comment>
}