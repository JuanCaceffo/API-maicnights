package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import java.time.LocalDateTime
import java.util.*

interface ShowDateRepository: MongoRepository<ShowDate, String> {
    override fun findById(id: String): Optional<ShowDate>

    override fun findAll(): List<ShowDate>

    @Query("{ 'date' : { '\$gte' : ?0, '\$lt' : ?1 }, 'show._id' : ?2 }")
    fun getByDateAndShowId(date: LocalDateTime, nextDay: LocalDateTime, showId: String): Optional<ShowDate>

    fun findAllByShowId(showId: String): List<ShowDate>

    @Query(value =  """{'show.id' : ?0}""", fields =  """{ id: 0 }""")
    fun showDateIdsByShowId(showId: String): List<String>


    //TODO: ver como implementar metodos de obtencion de costos de un show con mongo
//    @Query(
//        """
//           SELECT
//                SUM(SD.show.cost)
//                FROM ShowDate SD
//                WHERE SD.show.id = :id
//        """
//    )
//    fun showCost(@Param("id") id:Long): Optional<Double>
}