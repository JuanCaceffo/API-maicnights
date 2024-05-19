package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import org.springframework.data.mongodb.repository.MongoRepository
import java.time.LocalDateTime
import java.util.*

interface ShowDateRepository: MongoRepository<ShowDate, String> {
    override fun findById(id: String): Optional<ShowDate>

    override fun findAll(): MutableList<ShowDate>

    fun findByDateAndShowId(date: LocalDateTime, showId: String): Optional<ShowDate>

    fun findAllByShowId(showId: String): Iterable<ShowDate>
}