package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.ShowCommentError
import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentService {
    @Autowired
    lateinit var commentsRepository: CommentRepository

    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Transactional(Transactional.TxType.NEVER)
    fun findAll(): Iterable<Comment> = commentsRepository.findAll()

    @Transactional(Transactional.TxType.NEVER)
    fun getUserComments(id:Long): Iterable<Comment> = commentsRepository.findByUserId(id)
    @Transactional(Transactional.TxType.NEVER)
    fun findCommentByShowId(id: Long, sid: Long): Comment {
        return validateOptionalIsNotNull(commentsRepository.findById(id))
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findCommentByUserId(id: Long, sid: Long): Comment {
        return validateOptionalIsNotNull(commentsRepository.findById(id))
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): Comment = validateOptionalIsNotNull(commentsRepository.findById(id))

    @Transactional(Transactional.TxType.REQUIRED)
    fun addComment(dto: CommentDTO) {
        val show = validateOptionalIsNotNull(showRepository.findById(dto.showId))
        val user = validateOptionalIsNotNull(userRepository.findById(dto.userId))
        validateShowAvaiableToComment(dto.date)
        commentsRepository.save(Comment(user, show, dto))
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun removeComment(id:Long){
        val comment = validateOptionalIsNotNull(commentsRepository.findById(id))
        commentsRepository.delete(comment)
    }

    private fun validateShowAvaiableToComment(date: LocalDateTime) {
        if (date > LocalDateTime.now()) {
            throw BusinessException(ShowCommentError.SHOWDATE_NOT_PASSED)
        }
    }
}