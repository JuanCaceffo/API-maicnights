package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.dto.ShowDTO
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
class ShowController {
    @Autowired
    lateinit var showService: ShowService

    @GetMapping("/available-shows")
    @Operation(summary = "Devuelve todos los disponibles")
    fun getAvailable( ) : List<ShowDTO> = showService.getAvailable()

}