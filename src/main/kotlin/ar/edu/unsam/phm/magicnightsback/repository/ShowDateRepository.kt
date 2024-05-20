package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*

interface ShowDateRepository: MongoRepository<ShowDate, String> {
    override fun findById(id: String): Optional<ShowDate>

    override fun findAll(): List<ShowDate>

    @Query("{ 'date' : { '\$gte' : ?0, '\$lt' : ?1 }, 'show._id' : ?2 }")
    fun getByDateAndShowId(date: LocalDateTime, nextDay: LocalDateTime, showId: String): Optional<ShowDate>

    // Método que ajusta la fecha para obtener el rango completo de un día
    fun getByDateAndShowId(date: LocalDate, showId: String): Optional<ShowDate> {
        val startOfDay = LocalDateTime.of(date, LocalTime.MIN)
        val endOfDay = LocalDateTime.of(date.plusDays(1), LocalTime.MIN)
        return getByDateAndShowId(startOfDay, endOfDay, showId)
    }

    fun findAllByShowId(showId: String): List<ShowDate>
}