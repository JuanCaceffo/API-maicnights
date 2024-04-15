//package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.dto.CommentDTO
//import ar.edu.unsam.phm.magicnightsback.dto.toShowCommentDto
//import ar.edu.unsam.phm.magicnightsback.service.CommentService
//import io.swagger.v3.oas.annotations.Operation
//import org.springframework.web.bind.annotation.CrossOrigin
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.RequestParam
//import org.springframework.web.bind.annotation.RestController
//
//@RestController
//@CrossOrigin(origins = ["*"])
//class CommentController {
//    lateinit var comentService: CommentService
//    @GetMapping("/comment/{id}/show/{sid}")
//    @Operation(summary = "Devuelve un comentario según su id")
//    fun findCommentByShowId(@PathVariable id: Long, @PathVariable sid:Long):CommentDTO {
//        return comentService.findCommentByShowId(id, sid).toShowCommentDto()
//    }
//
//    @GetMapping("/comment/{id}/user/{id}")
//    @Operation(summary = "Devuelve un comentario según su id")
//    fun findCommentByUserId(@PathVariable id: Long, @RequestParam(required = true) uid:Long):CommentDTO {
//        return comentService.findCommentByUserId(id, uid).toShowCommentDto()
//    }
//}