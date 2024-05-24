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
    @Autowired var showService: ShowService,
    @Autowired var showDateService: ShowDateService,
    @Autowired var commentService: CommentService,
    @Autowired var userService: UserService,
    val hydrousService: HydrousService,
    @Autowired var ticketService: TicketService
) {
    @GetMapping("/{id}")
    @Operation(summary = "Returns a show by id")
    fun findShowById(
        @PathVariable id: String
    ): ShowDetailsDTO {
        val commentsStats = commentService.getCommentStadisticsOfShow(id)
        val dates = showDateService.findAllByShowId(id).map { it.toDTO() }
        val showComments = commentService.findByShowId(id)
        val soldOutStatus = showService.isSoldOut(id)

        val stats = ShowDetailsExtraDataDTO(
            commentsStats.rating,
            commentsStats.totalComments,
            dates,
            soldOutStatus,
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
    fun addPendingAttendee(@PathVariable id: String) {
        showService.addPendingAttendee(id)
    }

    @GetMapping("{id}/showdates")
    @Operation(summary = "Returns all available show dates for a show")
    fun findShowDatesByShowId(@PathVariable id: String) =
        showDateService.findAllByShowId(id).map { it.toDTO() }

    @GetMapping("{showId}/user/{userId}/kpi")
    @Operation(summary = "Returns KPIs of a Show")
    fun getKPIs(@PathVariable showId: Long, @PathVariable userId: Long): List<ShowStatsDTO> {
        userService.validateAdminStatus(userId)
        return showService.getKPIs(showId)
    }

    @GetMapping("{showId}/user/{userId}")
    @Operation(summary = "Show details (Admin)")
    fun getShowByIdForAdmin(@PathVariable showId: Long, @PathVariable userId: Long): ShowDetailsDTO {
        userService.validateAdminStatus(userId)

        val show = showService.findByIdOrError(showId)
        val ticketsSold = ticketService.ticketCountByShowId(showId)
        val showSales = ticketService.totalShowSales(showId)
        val pendingAttendees = show.pendingAttendees
        val showCost = showDateService.showCost(showId)
        val commentsStats = commentService.getCommentStadisticsOfShow(showId)
        val dates = showDateService.findAllByShowId(showId).map { it.toDTO() }
        val seats = show.facility.seats
        val soldOutStatus = showService.isSoldOut(showId)

        val stats = ShowDetailsExtraDataDTO(
            commentsStats.rating,
            commentsStats.totalComments,
            dates,
            soldOutStatus
        )

        val adminSummary =
            listOf(
                AdminSummary("Entradas vendidas totales:", ticketsSold.toDouble())
            ) +
                    seats.map {
                        AdminSummary(
                            "Entradas vendidas " + it.type.name + ":",
                            ticketService.ticketCountByShowIdAndSeatId(showId, it.id).toDouble()
                        )
                    } + listOf(
                AdminSummary("Recaudacion Total:", showSales),
                AdminSummary("Costo Total:", showCost),
                AdminSummary("Gente en Espera:", pendingAttendees.toDouble()),
            )

        return showService.findByIdOrError(showId).toShowAdminDetailsDTO(stats, adminSummary)
    }

    @PostMapping("/new-show-date")
    @Operation(summary = "Adds a new show date.")
    fun createShowDate(@RequestBody body: ShowDateRequest) {
        userService.validateAdminStatus(body.userId)
        showService.newShowDate(body.showId, body.date)
    }

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
