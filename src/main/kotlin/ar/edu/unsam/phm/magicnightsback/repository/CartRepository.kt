//package ar.edu.unsam.phm.magicnightsback.repository
//
//import ar.edu.unsam.phm.magicnightsback.domain.Ticket
//import ar.edu.unsam.phm.magicnightsback.dto.TicketResult
//import ar.edu.unsam.phm.magicnightsback.dto.UserBalanceDTO
//import org.springframework.data.jpa.repository.EntityGraph
//import org.springframework.data.jpa.repository.Query
//import org.springframework.data.repository.CrudRepository
//import org.springframework.data.repository.query.Param
//import java.util.*
//
////interface CartRepository : CrudRepository<Cart, Long> {
////    @EntityGraph(
////        attributePaths = [
////            "reservedTickets",
////            "reservedTickets.show",
////            "reservedTickets.show.dates",
////            "reservedTickets.show.dates.attendees",
////            "reservedTickets.show.facility.places",
////            "reservedTickets.showDate",
////            "reservedTickets.show.band"
////        ]
////    )
////    override fun findById(id: Long): Optional<Cart>
////
////    @Query("""
////        SELECT ti
////        FROM Cart c
////        INNER JOIN c.reservedTickets ti
////        WHERE c.user.id = :userId
////    """)
////    fun getReservedTickets(@Param("userId") userId: Long): List<Ticket>
////}