package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentStadisticsDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toShowCommentDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toUserCommentDTO
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.exceptions.DeleteError
import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
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

    
    fun findById(id: Long): Comment? =
        commentsRepository.findById(id).getOrNull()

    
    fun findByIdOrError(id: Long): Comment =
        findById(id) ?: throw ResponseFindException(FindError.NOT_FOUND(id, Comment::class.stringMe()))

    
    fun findByUserId(id: Long): Set<CommentDTO> =
        commentsRepository.findByUserId(id).map { it.toUserCommentDTO() }.toSet()

    

    fun findByShowId(id: Long): Set<CommentDTO> =
        commentsRepository.findByShowId(id).map { it.toShowCommentDTO() }.toSet()

    @Transactional(Transactional.TxType.REQUIRED)
    fun removeComment(userId: Long, commentId: Long) {
        val comment = findByIdOrError(commentId)
        if (!comment.canBeDeletedBy(userId)) throw BusinessException(
            DeleteError.CANNOT_DELETE(
                commentId,
                Comment::class.stringMe()
            )
        )
        commentsRepository.delete(comment)
    }

    //    
//    fun findCommentByShowId(id: Long, sid: Long): Comment {
//        return validateOptionalIsNotNull(commentsRepository.findById(id))
//    }
//
    
    fun getCommentStadisticsOfShow(id: Long): CommentStadisticsDTO {
        val totalRating = commentsRepository.averageCommentRatingOfShow(id).getOrNull() ?: 0.0
        val totalComments = commentsRepository.countAllByShowId(id)
        return CommentStadisticsDTO(totalRating.truncate(1), totalComments)
    }

//
//    
//    fun findCommentByUserId(id: Long, sid: Long): Comment {
//        return validateOptionalIsNotNull(commentsRepository.findById(id))
//    }
//

//
////    @Transactional(Transactional.TxType.REQUIRED)
////    fun addComment(commentCreate: CommentCreateDTO) {
////        val show = validateOptionalIsNotNull(showRepository.findById(commentCreate.showId))
////        val user = validateOptionalIsNotNull(userRepository.findById(commentCreate.userId))
////        validateShowAvaiableToComment(commentCreate.showDateId,user,show)
////        commentsRepository.save(Comment(user, show, commentCreate.text, commentCreate.rating))
////    }
//

//
////    private fun validateShowAvaiableToComment(showDateId: Long, user: User, show: Show) {
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
//    fun isAlreadyCommented(userId:Long, showId: Long) =  commentsRepository.countByUserIdAndShowId(userId, showId) > 0
}