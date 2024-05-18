package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.time.LocalDateTime
import java.util.*

interface ShowDateRepository: CrudRepository<ShowDate, Long> {
    @EntityGraph(attributePaths = [
        "show",
        "show.band",
        "show.facility",
        "show.facility.seats"
    ])
    override fun findById(id: Long): Optional<ShowDate>

    @EntityGraph(attributePaths = [
        "show",
        "show.band",
        "show.facility",
        "show.facility.seats"
    ])
    override fun findAll(): Iterable<ShowDate>

    fun findByDateAndShowId(date: LocalDateTime, showId: Long): Optional<ShowDate>

    fun findAllByShowId(showId:Long): Iterable<ShowDate>
}