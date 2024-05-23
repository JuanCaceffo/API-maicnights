package ar.edu.unsam.phm.magicnightsback.controller


import ar.edu.unsam.phm.magicnightsback.domain.dto.*
import ar.edu.unsam.phm.magicnightsback.exceptions.NotFoundException
import ar.edu.unsam.phm.magicnightsback.service.*
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
    var commentService: CommentService,
    private val hydrousService: HydrousService
) {
    @GetMapping("/{id}")
    @Operation(summary = "Returns a show by id")
    fun findShowById(
        @PathVariable id: String
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

        // Se actualizan los clicks en el show
        showService.addClick(id)

        return hydrousService.getHydrousShow(showService.findById(id)!!).toShowDetailsDTO(stats) ?: throw NotFoundException("") //TODO: return showService.findByIdOrError(id).toShowDetailsDTO(stats)
    }


    @GetMapping
    @Operation(summary = "Returns all available shows")
    fun findAll(@ModelAttribute request: ShowRequest = ShowRequest()): List<ShowDTO> {
        return showService.findAll(request).map {
            it.toDTO(showService.getShowExtraData(it.id, request.userId))
        }
    }

    @PatchMapping("{id}/add_pending")
    @Operation(summary = "Adds a pending Attendee to a Show")
    fun addPendingAttendee(@PathVariable id: String, @RequestParam userId: Long) {
        showService.addPendingAttendee(id)
    }

    @GetMapping("{id}/showdates")
    @Operation(summary = "Returns all available show dates for a show")
    fun findShowDatesByShowId(@PathVariable id: String) =
        showDateService.findAllByShowId(id).map { it.toDTO() }

//    @GetMapping("{id}/kpi")
//    @Operation(summary = "Returns KPIs of a Show")
//    fun getKPIs(@PathVariable id: String) = showService.getKPIs(id)

    data class ShowRequest(
        @RequestParam(required = false) val userId: Long = 0L,
        @RequestParam(required = false) val bandKeyword: String = "",
        @RequestParam(required = false) val facilityKeyword: String = "",
        @RequestParam(required = false, defaultValue = "false") val withFriends: Boolean = false
    )
}



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

////    @GetMapping("/admin/show/{showId}")
////    @Operation(summary = "Detalles de un show (dashboard Admin)")
////    fun getShowByIdForAdmin(@PathVariable showId: String, @RequestParam userId: Long): ShowAdminDetailsDTO {
////        return showService.findByIdAdmin(showId, userId).toShowAdminDetailsDTO()
////    }

////    @PostMapping("/admin/show/{showId}/new-show-date")
////    @Operation(summary = "Permite agregar una fecha")
////    @ApiResponses(
////        value = [
////            ApiResponse(responseCode = "200", description = "Ok"),
////            ApiResponse(responseCode = "400", description = ShowDateError.NEW_SHOW_INVALID_CONDITIONS),
////        ]
////    )
////    fun createShowDate(@PathVariable showId: String, @RequestParam userId: Long,@RequestBody body: ShowDateDTO) {
////       showService.createShowDate(showId, userId, body)
////    }