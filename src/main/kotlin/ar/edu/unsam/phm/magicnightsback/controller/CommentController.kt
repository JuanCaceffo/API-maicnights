//package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.dto.CommentCreateDTO
//import ar.edu.unsam.phm.magicnightsback.dto.CommentDTO
//import ar.edu.unsam.phm.magicnightsback.service.CommentService
//import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.tags.Tag
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.*
//
//@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
//@RestController
//@RequestMapping("/api/comment")
//@Tag(name = "Comment", description = "Comments related operations")
//class CommentController {
//    @Autowired
//    lateinit var commentService: CommentService
//
//    @GetMapping("user")
//    @Operation(summary = "Devuelve todos los comentarios disponibles para un usuario")
//    fun getUserComments(@RequestParam(required = true) userId: Long): List<CommentDTO> {
//        return commentService.getUserComments(userId)
//    }
//
////    @PostMapping("add-user-comment")
////    @Operation(summary = "Permite que un usuario comente un show")
////    fun addUserComment(@RequestBody commentData: CommentCreateDTO) {
////        commentService.addComment(commentData)
////    }
//
//    @GetMapping("show/{showId}")
//    @Operation(summary = "Devuelve los comentarios de un show")
//    fun getShowComments(@PathVariable showId: Long): List<CommentDTO> {
//        return commentService.getShowComments(showId)
//    }
//
//    @DeleteMapping("{commentId}/delete")
//    @Operation(summary = "Permite eliminar un comentario de un usuario")
//    fun deleteComment(@RequestParam(required = true) userId: Long, @PathVariable commentId: Long) {
//        commentService.removeComment(userId, commentId)
//    }
//}