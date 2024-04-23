package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
class SeatBootstrap : InitializingBean {
    @Autowired
    lateinit var seatRepository: SeatRepository

    val seats = listOf(
        Seat(SeatTypes.UPPERLEVEL),
        Seat(SeatTypes.FIELD),
        Seat(SeatTypes.BOX),
        Seat(SeatTypes.PULLMAN),
        Seat(SeatTypes.LOWERLEVEL)
    )

    fun createSeats() {
        seats.forEach {
            val seatTypeInRepo = seatRepository.findByName(it.name).getOrNull()

            if (seatTypeInRepo != null) {
                it.id = seatTypeInRepo.id
            } else {
                seatRepository.save(it)
                println("SeatType ${it.name} created")
            }
        }
    }
    @Bean("seat")
    override fun afterPropertiesSet() {
        println("Seats creation process starts")
        createSeats()
    }
}