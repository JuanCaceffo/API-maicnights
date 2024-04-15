package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Comment
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.validateOptionalIsNotNull
import ar.edu.unsam.phm.magicnightsback.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.RepositoryError
import ar.edu.unsam.phm.magicnightsback.error.ShowCommentError
import ar.edu.unsam.phm.magicnightsback.error.ShowError
import ar.edu.unsam.phm.magicnightsback.repository.CommentRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import jakarta.transaction.Transactional
import org.aspectj.apache.bcel.classfile.Module.Require
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class CommentsService(

) {
    @Autowired
    lateinit var commentsRepository: CommentRepository

    @Autowired
    lateinit var showService: ShowService

    @Autowired
    lateinit var userService: UserService

    @Transactional(Transactional.TxType.REQUIRED)
    fun addComment(comment: CommentDTO) {
        val show = showService.findById(comment.showId)
        val user = userService.findById(comment.userId)
        validateShowAvaiableToComment(show, comment.date)
        commentsRepository.save(Comment(user, show, comment))
    }

    private fun validateShowAvaiableToComment(show: Show, date: LocalDateTime){
        if (!show.isReadyToComment(date)) {
            throw BusinessException(ShowCommentError.SHOWDATE_NOT_PASSED)
        }
    }
}