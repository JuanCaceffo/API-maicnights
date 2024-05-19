package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.dto.SeatDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.service.SeatService
import ar.edu.unsam.phm.magicnightsback.service.ShowDateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("\${api.showdate}")
@Tag(name = "ShowDate", description = "Show date related operations")
class ShowDateController(
    @Autowired
    private val seatService: SeatService,

    @Autowired
    private val showDateService: ShowDateService
) {
    @GetMapping("/{id}/seats")
    @Operation(summary = "Returns show date seats")
    fun findAllSeatByShowDateId(@PathVariable id: String): List<SeatDTO> {
        val show = showDateService.findById(id)?.show//TODO: showDateService.findByIdOrError(id).show
        return seatService.findAllSeatByShowDateId(id).map { it.toDTO(show!!) } //TODO: Sacar !! cuando esté habilitada findByIdOrError
    }
}