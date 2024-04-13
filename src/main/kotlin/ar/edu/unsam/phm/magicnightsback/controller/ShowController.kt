//package ar.edu.unsam.phm.magicnightsback.controller
//
//import ar.edu.unsam.phm.magicnightsback.domain.AdminStats
//import ar.edu.unsam.phm.magicnightsback.dto.*
//import ar.edu.unsam.phm.magicnightsback.error.showDateError
//import ar.edu.unsam.phm.magicnightsback.service.ShowService
//import ar.edu.unsam.phm.magicnightsback.service.UserService
//import io.swagger.v3.oas.annotations.Operation
//import io.swagger.v3.oas.annotations.responses.ApiResponse
//import io.swagger.v3.oas.annotations.responses.ApiResponses
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.web.bind.annotation.*
//import org.springframework.web.bind.annotation.CrossOrigin
//import java.time.LocalDateTime
//import java.time.LocalDate
//import java.time.format.DateTimeFormatter
//
//@RestController
//@CrossOrigin(origins = ["*"])
//class ShowController {
//    @Autowired
//    lateinit var showService: ShowService
//    @Autowired
//    lateinit var userService: UserService
//
//    @GetMapping("/shows")
//    @Operation(summary = "Devuelve todos los disponibles")
//    fun getAll(@RequestParam(required = false, defaultValue = "-1") userId: Long,
//               @RequestParam(name = "bandKeyword", required = false, defaultValue = "") bandKeyword: String,
//               @RequestParam(name = "facilityKeyword", required = false, defaultValue = "") facilityKeyword: String,
//               @RequestParam(name = "withFriends", required = false, defaultValue = "false") withFriends: Boolean): List<ShowDTO> {
//
//        val params = BaseFilterParams(userId, bandKeyword, facilityKeyword, withFriends)
//        return showService.getAll(params)
//            .map { it.toShowDTO(showService.getAPossibleUserById(userId)) }
//    }
//
//    @GetMapping("/show/{id}")
//    @Operation(summary = "Devuelve un show según su id")
//    fun getShowById(
//        @PathVariable id: Long,
//        @RequestParam(required = false, defaultValue = "-1") userId: Long
//    ): ShowDTO {
//        val show = showService.getById(id)
//        val comments = show.allCommentsDTO()
//        return show.toShowDTO(showService.getAPossibleUserById(userId),comments)
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
//    @GetMapping("/admin_dashboard/shows/")
//    @Operation(summary = "Devuelve todos los shows disponibles para dashboard Admin")
//    fun getAllforAdmin(@RequestParam(required = false, defaultValue = "-1") userId: Long,
//                       @RequestParam(name = "bandKeyword", required = false, defaultValue = "") bandKeyword: String,
//                       @RequestParam(name = "facilityKeyword", required = false, defaultValue = "") facilityKeyword: String): List<ShowAdminDTO> {
//        userService.isAdmin(userId)
//        val params = BaseFilterParams(userId, bandKeyword, facilityKeyword)
//        return showService.getAll(params)
//            .map { it.toShowAdminDTO() }
//    }
//
//    @GetMapping("/admin_dashboard/shows/{id}")
//    @Operation(summary = "Devuelve los stats de un show según su id")
//    fun getShowStatsById(@PathVariable id: Long, @RequestParam(required = true, defaultValue = "-1") userId: Long): List<ShowStatsDTO> {
//        userService.isAdmin(userId)
//        val show = showService.getById(id)
//        return AdminStats.getAllStats(show)
//    }
//}