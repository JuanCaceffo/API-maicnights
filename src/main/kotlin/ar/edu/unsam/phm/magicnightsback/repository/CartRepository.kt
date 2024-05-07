package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import ar.edu.unsam.phm.magicnightsback.dto.TicketResult
import ar.edu.unsam.phm.magicnightsback.dto.UserBalanceDTO
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import java.util.*

interface CartRepository : CrudRepository<Cart, Long> {
    @EntityGraph(
        attributePaths = [
            "reservedTickets",
            "reservedTickets.show",
            "reservedTickets.show.dates",
            "reservedTickets.show.dates.attendees",
            "reservedTickets.show.facility.places",
            "reservedTickets.showDate",
            "reservedTickets.show.band"
        ]
    )
    override fun findById(id: Long): Optional<Cart>

    @Query(
        """
    SELECT ti.quantity AS quantity, 
    ti.id AS ticketId, 
    ti.show_date_id AS showDateId, 
    ti.show_id AS ShowId,
    ti.seat AS seat
    FROM public.ticket AS ti
    JOIN public.cart_reserved_tickets AS ct ON ct.reserved_tickets_id = ti.id
    JOIN public.cart AS c ON c.id = ct.cart_id
    JOIN public.show_date AS sd ON sd.id = ti.show_date_id
    WHERE c.id = :userId
        """, nativeQuery = true
    )
    fun getReservedTickets(@Param("userId") userId: Long): List<TicketResult>
}