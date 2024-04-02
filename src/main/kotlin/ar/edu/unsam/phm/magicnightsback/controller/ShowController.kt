package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.dto.*
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.LocalDate
import java.time.LocalDateTime

@RestController
@CrossOrigin(origins = ["*"])
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @Autowired
    lateinit var userService: UserService

    @GetMapping("/shows")
    @Operation(summary = "Devuelve todos shows los disponibles")
    fun getAll(@RequestParam(required = false, defaultValue = "-1") userId: Long): List<ShowDTO> {
        return showService.getAll()
            .map { it.toShowDTO(userId) }
    }

    @GetMapping("/show/{id}")
    @Operation(summary = "Devuelve un show según su id")
    fun getById(@PathVariable id: Long, @RequestParam(required = false) date: LocalDate?): ShowDetailsDTO {
        val show = showService.getById(id)
        val seats = show.facility.seats.map{
            SeatDTO(
                it.seatType.toString(),
                show.fullTicketPrice(it.seatType),
                date?.let {d -> show.getShowDate(d)?.availableSeatsOf(it.seatType) } ?: 0
            )
        }

        val comments = show.comments.map{
            CommentDTO(
                it.user.profileImage,
                it.user.username,
                show.showImg,
                it.band.name,
                it.text,
                it.rating
            )
        }
        return show.toShowDetailsDTO(seats,comments)
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
}