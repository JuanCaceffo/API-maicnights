package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.dto.SeatDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.service.CartService
import ar.edu.unsam.phm.magicnightsback.service.SeatService
import ar.edu.unsam.phm.magicnightsback.service.ShowDateService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@CrossOrigin(origins = ["*"], allowedHeaders = ["*"])
@RestController
@RequestMapping("\${api.showdate}")
@Tag(name = "ShowDate", description = "Show date related operations")
class ShowDateController(
    private val seatService: SeatService,
    private val showDateService: ShowDateService,
    private val cartService: CartService
) {
    @GetMapping("/{id}/seats")
    @Operation(summary = "Returns show date seats")
    fun findAllSeatByShowDateId(@PathVariable id: String): List<SeatDTO> {
        val showDate = showDateService.findById(id)!! //TODO: tirar error si no se encuentar el showdate
        return seatService.findAllSeatByShowDateId(id)
            .map { it.toDTO(showDate.show, showDate.available(cartService.seatReservations(it))) }
    }
}