package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Ticket
import org.springframework.stereotype.Repository

@Repository
class TicketRepository : CustomRepository<Ticket>()
