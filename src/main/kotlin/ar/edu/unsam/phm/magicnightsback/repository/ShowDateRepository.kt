package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

interface ShowDateRepository: MongoRepository<ShowDate, String> {
    override fun findById(id: String): Optional<ShowDate>

    override fun findAll(): List<ShowDate>

    @Query("{ date: {'\$regex': ?0} }")
    fun getByDateAndShowId(date: String, showId: String): Optional<ShowDate>

    fun findAllByShowId(showId: String): List<ShowDate>
}