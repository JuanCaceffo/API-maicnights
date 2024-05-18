package ar.edu.unsam.phm.magicnightsback.controller

import ar.edu.unsam.phm.magicnightsback.domain.Band
import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.exceptions.BusinessException
import ar.edu.unsam.phm.magicnightsback.repository.BandRepository
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import ar.edu.unsam.phm.magicnightsback.service.TicketService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("\${api.base}/test")
@Tag(name = "Testing", description = "Testing endpoints")
class TestController(
    private val bandRepository: BandRepository,
    private val facilityRepository: FacilityRepository,
    private val ticketService: TicketService
) {
    @GetMapping("/band/{name}")
    @Operation(summary = "Returns a bands by partial name keyword")
    fun getBand(@PathVariable name: String): List<Band> {
        return bandRepository.findByNameIsContainingIgnoreCase(name).map{it}
    }

    @GetMapping("/facility/{name}")
    @Operation(summary = "Returns a band by partial name keyword")
    fun getFacility(@PathVariable name: String): List<Facility> {
        return facilityRepository.findByNameIsContainingIgnoreCase(name).map{it}
    }

    @GetMapping("/friends_count/{showId}/{userId}")
    fun getFacility(@PathVariable showId: Long, @PathVariable userId: Long): Int {
        return ticketService.countFriendsAttendingToShow(showId, userId)
    }



//    @GetMapping("/attendees/{showId}")
//    @Operation(summary = "Returns users with show tickets")
//    fun getUsersInShow(@PathVariable showId: Long): List<Long> {
//        return ticketService.findUsersAttendingToShow(showId)
//    }
}