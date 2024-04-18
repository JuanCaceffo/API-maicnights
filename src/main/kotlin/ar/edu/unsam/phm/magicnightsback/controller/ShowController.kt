package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.domain.AdminStats
//import ar.edu.unsam.phm.magicnightsback.dto.*
//import ar.edu.unsam.phm.magicnightsback.error.showDateError
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.service.CommentService
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.UserService
//import ar.edu.unsam.phm.magicnightsback.service.UserService
import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.responses.ApiResponse
//import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

//import java.time.LocalDateTime
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api/show")
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
    fun getAll(@ModelAttribute request: ShowRequest): List<ShowUserDTO> {
        return showService.findAll(request).map {
            val commentsStats = commentService.getCommentStadisticsOfShow(it.id)
            it.toShowUserDTO(commentsStats)
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Devuelve un show según su id")
    fun getShowById(
        @PathVariable id: Long,
        @RequestParam userId: Long? = 0
    ): ShowDetailsDTO {
        val commentsStats = commentService.getCommentStadisticsOfShow(id)
        return showService.findById(id).toShowDetailsDTO(commentsStats)
    }

    @GetMapping("/admin_dashboard/shows/")
    @Operation(summary = "Devuelve todos los shows disponibles para dashboard Admin")
    fun getAllforAdmin(@ModelAttribute request: ShowAdminRequest): List<ShowDTO> {
        return showService.findAllAdmin(request).map { it.toShowDTO() }
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

//    @GetMapping("/admin_dashboard/shows/{id}")
//    @Operation(summary = "Devuelve los stats de un show según su id")
//    fun getShowStatsById(
//        @PathVariable id: Long,
//        @RequestParam(required = true, defaultValue = "-1") userId: Long
//    ): List<ShowStatsDTO> {
//        userService.validateAdmin(userId)
//        val show = showService.findById(id)
//        return AdminStats.getAllStats(show)
//    }


//
//    @GetMapping("/show_dates/{id}")
//    @Operation(summary = "Devuelve los datos por cada fecha de un show según su id")
//    fun getShowDatesById(@PathVariable id: Long, @RequestParam date: String): List<SeatDTO> {
//        val show = showService.getById(id)
//        val seats = show.getSeatTypes()
//        val showDate = show.getShowDate(parseLocalDate(date))
//
//        val toSeatsDto = seats.map {
//            SeatDTO(
//                it.toString(),
//                show.ticketPrice(it),
//                showDate!!.availableSeatsOf(it)
//            )
//        }
//        return toSeatsDto
//    }
//
//    fun parseLocalDate(dateString: String): LocalDate {
//        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//        return LocalDate.parse(dateString, formatter)
//    }
//
//    fun parseLocalDateTime(dateString: String): LocalDateTime {
//        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
//        return LocalDateTime.parse(dateString, formatter)
//    }
//
//    @PostMapping("/show/{showId}/create-show-date")
//    @Operation(summary = "Permite agregar una fecha si el usuario es administrador")
//    @ApiResponses(
//        value = [
//            ApiResponse(responseCode = "200", description = "Ok"),
//            ApiResponse(responseCode = "400", description = showDateError.NEW_SHOW_INVALID_CONDITIONS),
//        ]
//    )
//    fun createShowDate(@PathVariable showId: Long, @RequestBody body: ShowDateDTO) {
//        println(body)
//        userService.isAdmin(body.userId)
//        showService.createShowDate(showId, body.userId, parseLocalDateTime(body.date))
//    }
//

//

}