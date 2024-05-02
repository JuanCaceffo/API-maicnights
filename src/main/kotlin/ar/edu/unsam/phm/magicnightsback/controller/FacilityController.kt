package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.service.ShowService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("/api/facility")
@Tag(name = "Facility", description = "Facility related operations")
class FacilityController {
    @Autowired
    lateinit var showService: ShowService

    @GetMapping("/busy")
    @Operation(summary = "Devuelve las facilities q tienen mas de un Show realizado")
    fun busyFacilities(): List<Facility> {
        val busyFacilities = showService.getBusyFacilities()
        return busyFacilities
    }

}