package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.error.UserError
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.time.LocalDateTime
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.serializers.View
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("show")
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @GetMapping("/available-shows")
    @Operation(summary = "Devuelve todos los disponibles")
    @JsonView(View.Iterable.Show.Plain::class)
    fun getAvailable( ) : Iterable<Show> = showService.getAvailable()

    @PostMapping("/{showId}/create-date/user/{userId}")
    @Operation(summary = "Permite agregar un show si el usuario es administrador")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "400", description = UserError.USER_NOT_AUTHORIZED_CREATE_DATE),
    ])
    fun createShowDate(@PathVariable showId: Long, @PathVariable userId: Long, @RequestBody date: LocalDateTime){
        showService.createShowDate(showId, userId, date)
    }
}