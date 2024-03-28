package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.service.ShowService
import ar.edu.unsam.phm.magicnightsback.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
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

    @GetMapping("/{showId}/create-date/user/{id}")
    fun createShowDate(@PathVariable showId: Int, @PathVariable userId: Int, @RequestBody date: LocalDate){
        showService.createShowDate(showId, userId, date)
    }

}