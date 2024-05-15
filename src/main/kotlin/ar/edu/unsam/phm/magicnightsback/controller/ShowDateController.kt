package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.dto.SeatDTO
import ar.edu.unsam.phm.magicnightsback.domain.dto.toDTO
import ar.edu.unsam.phm.magicnightsback.service.SeatService
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
    private val seatService: SeatService
) {
    @GetMapping("/{id}/seats")
    @Operation(summary = "Returns show date seats")
    fun findAllSeatByShowDateId(@PathVariable id: Long): List<SeatDTO> =
        seatService.findAllSeatByShowDateId(id).map { it.toDTO() }
}


////    @GetMapping("/show_dates/{showId}/date/{dateId}")
////    @Operation(summary = "Devuelve los asientos por cada fecha de un show")
////    fun getSeatsByShowDateId(
////        @PathVariable showId: Long,
////        @PathVariable dateId: Long
////    ): List<PlaceDTO> {
////        val show = showService.findById(showId)
////        val seats = show.getSeatTypes()
////        val showDate = show.getShowDateById(dateId)
////
////        return seats.map {
////            PlaceDTO(
////                it.id ?: 0,
////                it.seatType.name,
////                show.ticketPrice(it.seatType),
////                showDate.availableSeatsOf(it.seatType)
////            )
////        }.sortedBy { seat -> seat.seatType }
////    }