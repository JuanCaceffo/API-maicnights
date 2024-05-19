package ar.edu.unsam.phm.magicnightsback.controller


import ar.edu.unsam.phm.magicnightsback.domain.dto.*
import ar.edu.unsam.phm.magicnightsback.service.CommentService
import ar.edu.unsam.phm.magicnightsback.service.ShowDateService
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.TicketService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("\${api.show}")
@Tag(name = "Show", description = "Show related operations")
class ShowController(
    @Autowired
    var showService: ShowService,

    @Autowired
    var showDateService: ShowDateService,


    @Autowired
    var ticketService: TicketService,

//    @Autowired
//    var userService: UserService,
//
    @Autowired
    var commentService: CommentService
) {
    @GetMapping("/{id}")
    @Operation(summary = "Returns a show by id")
    fun findShowById(
        @PathVariable id: Long
    ): ShowDetailsDTO {
        val commentsStats = commentService.getCommentStadisticsOfShow(id)
        val dates = showDateService.findAllByShowId(id).map { it.toDTO() }
        val showComments = commentService.findByShowId(id)

        val stats = ShowDetailsExtraDataDTO(
            commentsStats.rating,
            commentsStats.totalComments,
            dates,
            showComments
        )

        return showService.findByIdOrError(id).toShowDetailsDTO(stats)
    }


    @GetMapping
    @Operation(summary = "Returns all available shows")
    fun findAll(@ModelAttribute request: ShowRequest = ShowRequest()): List<ShowDTO> {
        return showService.findAll(request).map {
            it.toDTO(showService.getShowExtraData(it.id, request.userId))
        }
    }

    @GetMapping("{id}/showdates")
    @Operation(summary = "Returns all available show dates for a show")
    fun findShowDatesByShowId(@PathVariable id: Long) =
        showDateService.findAllByShowId(id).map { it.toDTO() }



    data class ShowRequest(
        @RequestParam(required = false) val userId: Long = 0L,
        @RequestParam(required = false) val bandKeyword: String = "",
        @RequestParam(required = false) val facilityKeyword: String = "",
        @RequestParam(required = false, defaultValue = "false") val withFriends: Boolean = false
    )
}

////
////    @GetMapping("/show/{id}")
////    @Operation(summary = "Devuelve un show según su id")
////    fun getShowById(
////        @PathVariable id: Long,
////        @RequestParam userId: Long = 0
////    ): ShowDTO {
////        val commentsStats = commentService.getCommentStadisticsOfShow(id)
////        return showService.findById(id).toShowDetailsDTO(commentsStats)
////    }
////

////
////    @GetMapping("/admin/show/{id}/stats")
////    @Operation(summary = "Devuelve los stats de un show según su id (dashboard Admin)")
////    fun getShowStatsById(
////        @PathVariable id: Long,
////        @RequestParam(required = true, defaultValue = "-1") userId: Long
////    ): List<ShowStatsDTO> {
////        userService.validateAdminStatus(userId)
////        val show = showService.findById(id)
////        return show.getAllStats(show)
////    }
////
////    @GetMapping("/admin/show/{showId}")
////    @Operation(summary = "Detalles de un show (dashboard Admin)")
////    fun getShowByIdForAdmin(@PathVariable showId: Long, @RequestParam userId: Long): ShowAdminDetailsDTO {
////        return showService.findByIdAdmin(showId, userId).toShowAdminDetailsDTO()
////    }
////
////    @PostMapping("/admin/show/{showId}/new-show-date")
////    @Operation(summary = "Permite agregar una fecha")
////    @ApiResponses(
////        value = [
////            ApiResponse(responseCode = "200", description = "Ok"),
////            ApiResponse(responseCode = "400", description = ShowDateError.NEW_SHOW_INVALID_CONDITIONS),
////        ]
////    )
////    fun createShowDate(@PathVariable showId: Long, @RequestParam userId: Long,@RequestBody body: ShowDateDTO) {
////       showService.createShowDate(showId, userId, body)
////    }
////
////    fun userOrNull(id: Long): User? {
////        return if (id != 0.toLong()) {
////            userService.findById(id)
////        } else {
////            null
////        }
////    }
////

//}