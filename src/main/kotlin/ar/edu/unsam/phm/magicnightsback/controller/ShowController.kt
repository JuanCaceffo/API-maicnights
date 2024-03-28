package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController()
@CrossOrigin(origins = ["*"])
@RequestMapping("show")
class ShowController(
    val showService: ShowService
) {

    @PostMapping("/{showId}/create-date/user/{userId}")
    @Operation(summary = "Permite agregar un show si el usuario es administrador")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Ok"),
        ApiResponse(responseCode = "400", description = UserError.USER_NOT_AUTHORIZED_CREATE_DATE),
    ])
    fun createShowDate(@PathVariable showId: Int, @PathVariable userId: Int, @RequestBody date: LocalDate){
        showService.createShowDate(showId, userId, date)
    }
}