package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
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
    fun createShowDate(@PathVariable showId: Int, @PathVariable userId: Int, @RequestBody date: LocalDate){
        showService.createShowDate(showId, userId, date)
    }
}