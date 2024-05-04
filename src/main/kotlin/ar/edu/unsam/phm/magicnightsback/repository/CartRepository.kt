package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CartRepository: CrudRepository<Cart, Long> {
@EntityGraph(attributePaths = [
        "reservedTickets",
        "reservedTickets.show",
        "reservedTickets.show.dates",
        "reservedTickets.show.facility.places",
        "reservedTickets.showDate",
        "reservedTickets.show.band"
    ])
    override fun findById(id: Long): Optional<Cart>
}