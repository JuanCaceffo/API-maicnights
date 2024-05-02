package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.ShowDateError
import ar.edu.unsam.phm.magicnightsback.service.CommentService
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api")
@Tag(name = "Show", description = "Show related operations")
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @Autowired
    lateinit var userService: UserService

    @Autowired
    lateinit var commentService: CommentService

    @GetMapping("/shows")
    @Operation(summary = "Devuelve todos los shows disponibles")
    fun getAll(@ModelAttribute request: ShowRequest): List<ShowDTO> {
        return showService.findAll(request).map {
            val commentsStats = commentService.getCommentStadisticsOfShow(it.id)
            it.toShowDTO(commentsStats, userOrNull(request.userId))
        }
    }

    @GetMapping("/show/{id}")
    @Operation(summary = "Devuelve un show según su id")
    fun getShowById(
        @PathVariable id: Long,
        @RequestParam userId: Long = 0
    ): ShowDTO {
        val commentsStats = commentService.getCommentStadisticsOfShow(id)
        return showService.findById(id).toShowDetailsDTO(commentsStats)
    }

    @GetMapping("/show_dates/{showId}/date/{dateId}")
    @Operation(summary = "Devuelve los asientos por cada fecha de un show")
    fun getShowDatesById(
        @PathVariable showId: Long,
        @PathVariable dateId: Long
    ): List<PlaceDTO> {
        val show = showService.findById(showId)
        val seats = show.getSeatTypes()
        val showDate = show.getShowDateById(dateId)

        return seats.map {
            PlaceDTO(
                it.name,
                show.ticketPrice(it),
                showDate.availableSeatsOf(it)
            )
        }
    }

    @GetMapping("/admin/shows")
    @Operation(summary = "Devuelve todos los shows disponibles (dashboard Admin)")
    fun getAllforAdmin(@ModelAttribute request: ShowAdminRequest): List<ShowDTO> {
        return showService.findAllAdmin(request).map { it.toShowDTO() }
    }

    @GetMapping("/admin/show/{id}/stats")
    @Operation(summary = "Devuelve los stats de un show según su id (dashboard Admin)")
    fun getShowStatsById(
        @PathVariable id: Long,
        @RequestParam(required = true, defaultValue = "-1") userId: Long
    ): List<ShowStatsDTO> {
        userService.validateAdminStatus(userId)
        val show = showService.findById(id)
        return show.getAllStats(show)
    }

    @GetMapping("/admin/show/{showId}")
    @Operation(summary = "Detalles de un show (dashboard Admin)")
    fun getShowByIdForAdmin(@PathVariable showId: Long, @RequestParam userId: Long): ShowAdminDetailsDTO {
        return showService.findByIdAdmin(showId, userId).toShowAdminDetailsDTO()
    }

    @PostMapping("/admin/show/{showId}/new-show-date")
    @Operation(summary = "Permite agregar una fecha")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "400", description = ShowDateError.NEW_SHOW_INVALID_CONDITIONS),
        ]
    )
    fun createShowDate(@PathVariable showId: Long, @RequestParam userId: Long,@RequestBody body: ShowDateDTO) {
       showService.createShowDate(showId, userId, body)
    }

    fun userOrNull(id: Long): User? {
        return if (id != 0.toLong()) {
            userService.findById(id)
        } else {
            null
        }
    }

    class ShowRequest(
        @RequestParam val userId: Long = 0,
        @RequestParam val bandKeyword: String = "",
        @RequestParam val facilityKeyword: String = "",
        @RequestParam(required = false, defaultValue = "false") val withFriends: Boolean = false
    )

    class ShowAdminRequest(
        @RequestParam val userId: Long = 0,
        @RequestParam val bandKeyword: String = "",
        @RequestParam val facilityKeyword: String = "",
    ) {
        fun toShowRequest(): ShowRequest = ShowRequest(userId, bandKeyword, facilityKeyword)
    }
}