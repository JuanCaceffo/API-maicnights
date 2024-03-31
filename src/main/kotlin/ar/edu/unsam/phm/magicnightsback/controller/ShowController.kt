package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.serializers.View
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import com.fasterxml.jackson.annotation.JsonView
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("show")
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @GetMapping("/available-shows")
    @Operation(summary = "Devuelve todos los disponibles")
    @JsonView(View.Iterable.Show.Plain::class)
    fun getAll(): Iterable<Show> = showService.getAll()

}