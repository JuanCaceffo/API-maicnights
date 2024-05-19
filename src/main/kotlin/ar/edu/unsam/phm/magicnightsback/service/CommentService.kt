package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentStadisticsDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toShowCommentDTO
import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import ar.edu.unsam.phm.magicnightsback.utils.truncate
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

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

//    @Transactional(Transactional.TxType.NEVER)
//    fun getUserComments(id: Long): List<CommentDTO> = commentsRepository.findByUserId(id).map { it.toUserCommentDto() }
//
    @Transactional(Transactional.TxType.NEVER)
    fun getShowComments(id: String): Set<CommentDTO> = commentsRepository.findByShowId(id).map { it.toShowCommentDTO() }.toSet()

//    @Transactional(Transactional.TxType.NEVER)
//    fun findCommentByShowId(id: Long, sid: Long): Comment {
//        return validateOptionalIsNotNull(commentsRepository.findById(id))
//    }
//
    @Transactional(Transactional.TxType.NEVER)
    fun getCommentStadisticsOfShow(id: String): CommentStadisticsDTO {
        val totalRating = commentsRepository.averageCommentRatingOfShow(id).getOrNull() ?: 0.0
        val totalComments = commentsRepository.countAllByShowId(id)
        return CommentStadisticsDTO(totalRating.truncate(1), totalComments)
    }

//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findCommentByUserId(id: Long, sid: Long): Comment {
//        return validateOptionalIsNotNull(commentsRepository.findById(id))
//    }
//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findById(id: Long): Comment = validateOptionalIsNotNull(commentsRepository.findById(id))
//
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun addComment(commentCreate: CommentCreateDTO) {
////        val show = validateOptionalIsNotNull(showRepository.findById(commentCreate.showId))
////        val user = validateOptionalIsNotNull(userRepository.findById(commentCreate.userId))
////        validateShowAvaiableToComment(commentCreate.showDateId,user,show)
////        commentsRepository.save(Comment(user, show, commentCreate.text, commentCreate.rating))
////    }
//
//    @Transactional(Transactional.TxType.REQUIRED)
//    fun removeComment(userId: Long, commentId: Long) {
//        val comment = validateOptionalIsNotNull(commentsRepository.findById(commentId))
//        if (!comment.canBeDeletedBy(userId)) throw BusinessException(CommentError.INVALID_DELETE)
//        commentsRepository.delete(comment)
//    }
//
////    private fun validateShowAvaiableToComment(showDateId: String, user: User, show: Show) {
////        val showDate = show.getShowDateById(showDateId)
////        if (!showDate.isAttendee(user)){
////            throw BusinessException(CommentError.IS_NOT_ATTENDEE)
////        }
////        if (!showDate.datePassed()) {
////            throw BusinessException(CommentError.SHOWDATE_NOT_PASSED)
////        }
////        if (isAlreadyCommented(user.id,show.id)){
////            throw BusinessException(CommentError.SHOW_ALREADY_COMMENTED)
////        }
////    }
//
//    //fun canBeCommented(showDate: ShowDate, user: User, show: Show) = !isAlreadyCommented(user.id, show.id) && showDate.datePassed() && showDate.isAttendee(user)
//
//    fun isAlreadyCommented(userId:Long, showId: String) =  commentsRepository.countByUserIdAndShowId(userId, showId) > 0
}