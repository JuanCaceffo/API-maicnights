package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentCreateDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.CommentDTO
import ar.edu.unsam.phm.magicnightsback.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api/comment")
@Tag(name = "Comment", description = "Comments related operations")
class CommentController(
    @Autowired
    private var commentService: CommentService
) {
    @GetMapping("/user/{id}")
    @Operation(summary = "Get all user comments")
    fun findByUserId(@PathVariable id: Long): Set<CommentDTO> {
        return commentService.findByUserId(id)
    }

    @DeleteMapping("/{commentId}/user/{userId}")
    @Operation(summary = "Deletes a user comment")
    fun deleteComment(@PathVariable userId: Long, @PathVariable commentId: Long) {
        commentService.removeComment(userId, commentId)
    }

    @PostMapping("/add")
    @Operation(summary = "Permite que un usuario comente un show")
    fun addUserComment(@RequestBody commentData: CommentCreateDTO) {
        commentService.addComment(commentData)
    }
}