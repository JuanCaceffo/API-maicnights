package ar.edu.unsam.phm.magicnightsback.bootstrap

import ar.edu.unsam.phm.magicnightsback.domain.Seat
import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("baseBootstrap")
class SeatBootstrap : InitializingBean {
    @Autowired
    lateinit var seatRepository: SeatRepository

    fun createSeats() {
        SeatTypes.entries.forEach {
            seatRepository.save(Seat(it))
            println("SeatType ${it.name} created")
        }
    }

    @Bean("seat")
    override fun afterPropertiesSet() {
        println("Seats creation process starts")
        createSeats()
    }
}