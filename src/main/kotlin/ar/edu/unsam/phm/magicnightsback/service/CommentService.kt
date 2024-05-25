package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.domain.dto.*
import ar.edu.unsam.phm.magicnightsback.exceptions.*
import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import ar.edu.unsam.phm.magicnightsback.utils.truncate
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class CommentService(
    @Autowired var commentsRepository: CommentRepository,
    @Autowired var showRepository: ShowRepository,
    @Autowired var ticketService: TicketService
) {
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

//    fun findCommentByUserId(id: Long, sid: Long): Comment {
//        return validateOptionalIsNotNull(commentsRepository.findById(id))
//    }
    @Transactional(Transactional.TxType.REQUIRED)
    fun addComment(commentCreate: CommentCreateDTO) {
        val ticket = ticketService.findByIdOrError(commentCreate.ticketId)
        validateShowAvaiableToComment(ticket, commentCreate.userId)
        commentsRepository.save(Comment(ticket.user, ticket.showDate.show, commentCreate.text, commentCreate.rating))
    }

    private fun validateShowAvaiableToComment(ticket: Ticket, userId: Long) {
        if (ticket.user.id != userId){
            throw BusinessException(CreationError.IS_NOT_ATTENDEE)
        }
        if (!ticket.canBeCommented()) {
            throw BusinessException(CommentError.SHOWDATE_NOT_PASSED)
        }
        if (isAlreadyCommented(userId,ticket.showDate.show.id)){
            throw BusinessException(CommentError.SHOW_ALREADY_COMMENTED)
        }
    }
    fun isAlreadyCommented(userId:Long, showId: Long) =  commentsRepository.countByUserIdAndShowId(userId, showId) > 0
}