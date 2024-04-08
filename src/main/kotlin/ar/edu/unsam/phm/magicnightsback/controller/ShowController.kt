package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RestController
@CrossOrigin(origins = ["*"])
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @GetMapping("/shows")
    @Operation(summary = "Devuelve todos los disponibles")
    fun getAll(@RequestParam(required = false, defaultValue = "-1") userId: Long,
               @RequestParam(name = "bandKeyword", required = false, defaultValue = "") bandKeyword: String,
               @RequestParam(name = "facilityKeyword", required = false, defaultValue = "") facilityKeyword: String,
               @RequestParam(name = "withFriends", required = false, defaultValue = "false") withFriends: Boolean): List<ShowDTO> {

        val params = BaseFilterParams(userId, bandKeyword, facilityKeyword, withFriends)
        return showService.getAll(params)
            .map { it.toShowDTO(showService.getAPossibleUserById(userId)) }
    }

    @GetMapping("/show/{id}")
    @Operation(summary = "Devuelve un show según su id")
    fun getShowById(
        @PathVariable id: Long,
        @RequestParam(required = false, defaultValue = "-1") userId: Long
    ): ShowDTO {
        val show = showService.getById(id)

        val comments = show.allCommentsDTO()
        return show.toShowDTO(showService.getAPossibleUserById(userId),comments)
    }

    @GetMapping("/show_dates/{id}")
    @Operation(summary = "Devuelve los datos por cada fecha de un show según su id")
    fun getShowDatesById(@PathVariable id: Long, @RequestParam date: String): List<SeatDTO> {
        val show = showService.getById(id)
        val seats = show.getSeatTypes()
        val showDate = show.getShowDate(parseLocalDate(date))

        val toSeatsDto = seats.map {
            SeatDTO(
                it.toString(),
                show.ticketPrice(it),
                showDate!!.availableSeatsOf(it)
            )
        }
        return toSeatsDto
    }

    fun parseLocalDate(dateString: String): LocalDate {
        val formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
        return LocalDate.parse(dateString, formatter)
    }


//    @GetMapping("/showDates/{id}/")
//    @Operation(summary = "Devuelve los datos por cada fecha de un show según su id")
//    fun getShowDatesById(@PathVariable id: Long, @RequestParam date: LocalDate): ShowDateDetailsDTO {
//        val show = showService.getById(id)
//        val showDate = show.dates.find { it.date.toLocalDate() == date }
//            ?: throw NotFoundException("No hay datos disponibles para la fecha proporcionada.")
//
//        val dateSeats = DateSeatsDTO(
//            showDate.date,
//            show.facility.seats.map { seat ->
//                SeatsDTO(
//                    seat.seatType.toString(),
//                    show.fullTicketPrice(seat.seatType),
//                    show.getShowDate(showDate.date.toLocalDate())?.availableSeatsOf(seat.seatType) ?: 0
//                )
//            }
//        )
//
//        return show.toShowDateDetailsDTO(listOf(dateSeats))
//    }

    @PostMapping("/show/{showId}/create-date/user/{userId}")
    @Operation(summary = "Permite agregar un show si el usuario es administrador")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Ok"),
            ApiResponse(responseCode = "400", description = UserError.USER_NOT_AUTHORIZED_CREATE_DATE),
        ]
    )
    fun createShowDate(@PathVariable showId: Long, @PathVariable userId: Long, @RequestBody date: LocalDateTime) {
        showService.createShowDate(showId, userId, date)
    }

    @GetMapping("/admin_dashboard/shows/")
    @Operation(summary = "Devuelve todos los shows disponibles para dashboard Admin")
    fun getAllforAdmin(@RequestParam(required = false, defaultValue = "-1") userId: Long,
                       @RequestParam(name = "bandKeyword", required = false, defaultValue = "") bandKeyword: String,
                       @RequestParam(name = "facilityKeyword", required = false, defaultValue = "") facilityKeyword: String): List<ShowAdminDTO> {
        val params = BaseFilterParams(userId, bandKeyword, facilityKeyword)
        return showService.getAll(params)
            .map { it.toShowAdminDTO() }
    }

    @GetMapping("/admin_dashboard/shows/{id}")
    @Operation(summary = "Devuelve los stats de un show según su id")
    fun getShowStatsById(@PathVariable id: Long): ShowStatsDTO {
        val show = showService.getById(id)
        return show.toShowStatsDTO()
    }
}