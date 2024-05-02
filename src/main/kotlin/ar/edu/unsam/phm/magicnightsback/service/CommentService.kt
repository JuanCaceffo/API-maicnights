package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.CommentError
import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

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
    fun getUserComments(id: Long): List<CommentDTO> = commentsRepository.findByUserId(id).map { it.toUserCommentDto() }

    @Transactional(Transactional.TxType.NEVER)
    fun getShowComments(id: Long): List<CommentDTO> = commentsRepository.findByShowId(id).map { it.toShowCommentDto() }

    @Transactional(Transactional.TxType.NEVER)
    fun findCommentByShowId(id: Long, sid: Long): Comment {
        return validateOptionalIsNotNull(commentsRepository.findById(id))
    }

    @Transactional(Transactional.TxType.NEVER)
    fun getCommentStadisticsOfShow(id: Long): CommentStadisticsDTO {
        val totalRating = showRating(id)
        val totalComments = totalShowComments(id)
        return CommentStadisticsDTO(totalRating, totalComments)
    }

    @Transactional(Transactional.TxType.NEVER)
    fun showRating(id: Long) = showRatings(id).averageOrZero()

    private fun showRatings(id: Long) = getShowComments(id).map { it.rating }

    @Transactional(Transactional.TxType.NEVER)
    fun totalShowComments(id: Long) = getShowComments(id).size

    @Transactional(Transactional.TxType.NEVER)
    fun findCommentByUserId(id: Long, sid: Long): Comment {
        return validateOptionalIsNotNull(commentsRepository.findById(id))
    }

    @Transactional(Transactional.TxType.NEVER)
    fun findById(id: Long): Comment = validateOptionalIsNotNull(commentsRepository.findById(id))

    @Transactional(Transactional.TxType.REQUIRED)
    fun addComment(commentCreate: CommentCreateDTO) {
        val show = validateOptionalIsNotNull(showRepository.findById(commentCreate.showId))
        val user = validateOptionalIsNotNull(userRepository.findById(commentCreate.userId))
        validateShowAvaiableToComment(commentCreate.showDateId,user,show)
        commentsRepository.save(Comment(user, show, commentCreate.text, commentCreate.rating))
    }

    @Transactional(Transactional.TxType.REQUIRED)
    fun removeComment(userId: Long, commentId: Long) {
        val comment = validateOptionalIsNotNull(commentsRepository.findById(commentId))
        if (!comment.canBeDeletedBy(userId)) throw BusinessException(CommentError.INVALID_DELETE)
        commentsRepository.delete(comment)
    }

    private fun validateShowAvaiableToComment(showDateId: Long, user: User, show: Show) {
        val showDate = show.getShowDateById(showDateId)
        if (!showDate.isAttendee(user)){
            throw BusinessException(CommentError.IS_NOT_ATTENDEE)
        }
        if (!showDate.datePassed()) {
            throw BusinessException(CommentError.SHOWDATE_NOT_PASSED)
        }
        if (getUserComments(user.id).any { commentDto -> commentDto.showId == show.id }){
            throw BusinessException(CommentError.SHOW_ALREADY_COMMENTED)
        }
    }

}