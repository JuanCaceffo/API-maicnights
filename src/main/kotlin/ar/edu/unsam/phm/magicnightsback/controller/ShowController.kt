package ar.edu.unsam.phm.magicnightsback.controller

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

@RestController
@CrossOrigin(origins = ["*"])
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @GetMapping("/shows")
    @Operation(summary = "Devuelve todos shows los disponibles")
    fun getAll(@RequestParam(required = false, defaultValue = "-1") userId: Long): List<ShowDTO> {
        return showService.getAll()
            .map { it.toShowDTO(userId) }
    }

    @GetMapping("/show/{id}")
    @Operation(summary = "Devuelve un show según su id")
    fun getShowById(@PathVariable id: Long, @RequestParam(required = false, defaultValue = "-1") userId: Long): ShowDTO {
        val show = showService.getById(id)

        val comments = show.allCommentsDTO()
        return show.toShowDTO(userId,comments)
    }

    @GetMapping("/show_dates/{id}")
    @Operation(summary = "Devuelve los datos por cada fecha de un show según su id")
    fun getShowDatesById(@PathVariable id: Long): ShowDateDetailsDTO {
        val show = showService.getById(id)
        val dateSeats = show.dates.map{d ->
            DateSeatsDTO(
                d.date,
                show.facility.seats.map{seat ->
                    SeatsDTO(
                        seat.seatType.toString(),
                        show.fullTicketPrice(seat.seatType),
                        show.getShowDate(d.date.toLocalDate())?.availableSeatsOf(seat.seatType) ?: 0
                    )
                }
            )
        }
        return show.toShowDateDetailsDTO(dateSeats)
    }

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

    @GetMapping("/admin_dashboard/shows/{id}")
    @Operation(summary = "Devuelve todos los shows disponibles para dashboard Admin")
    fun getAllforAdmin(): List<ShowAdminDTO> {
        return showService.getAll()
            .map { it.toShowAdminDTO() }
    }

    @GetMapping("/admin_dashboard/shows/{id}/stats")
    @Operation(summary = "Devuelve los stats de un show según su id")
    fun getShowStatsById(@PathVariable id: Long): ShowStatsDTO {
        val show = showService.getById(id)
        return show.toShowStatsDTO()
    }
}