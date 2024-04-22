package ar.edu.unsam.phm.magicnightsback.bootstraptest

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.*
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class TestBootStrap : InitializingBean {
    @Autowired
    lateinit var seatRepository: SeatRepository

    @Autowired
    lateinit var bandRepository: BandRepository

    @Autowired
    lateinit var facilityRepository: FacilityRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var showRepository: ShowRepository

    val seats = listOf(
        Seat(SeatTypes.UPPERLEVEL),
        Seat(SeatTypes.FIELD),
        Seat(SeatTypes.BOX),
        Seat(SeatTypes.PULLMAN),
        Seat(SeatTypes.LOWERLEVEL)
    )

    val bands = listOf(Band("testband").apply { cost = 100000.0 })

    val facilities = listOf(Theater("testfacility", Point(1.0, 1.0)))

    val users =
        listOf(
            User("normaluser", "normaluser", "normaluser", "asdf"),
            User("adminuser", "adminuser", "adminuser", "asdf").apply { isAdmin = true }
        )

    fun createSeats() {
        seats.forEach {
            seatRepository.save(it)
            println("Seat ${it.name} added correctly")
        }
    }

    fun createBands() {
        bands.forEach {
            bandRepository.save(it)
            println("Band ${it.name} added correctly")
        }
    }

    fun createFacilities() {
        val seat = seatRepository.findByName(SeatTypes.PULLMAN.name).get()

        facilities.forEach {
            it.addPlace(seat, 2000)
            facilityRepository.save(it)
            println("Facility ${it.name} added correctly")
        }
    }

    fun createUser() {
        users.forEach {
            userRepository.save(it)
            println("User ${it.name} added correctly")
        }
    }

    fun createShows() {
        val band = bandRepository.findByName("testband").get()
        val facility = facilityRepository.findByName("testfacility").get()
        val shows = listOf(Show("testshow", band, facility))
        shows.forEach {
            showRepository.save(it)
            println("Show ${it.name} added correctly")
        }
    }


    override fun afterPropertiesSet() {
        println("Initializing Bootstrap for Testing")
        createUser()
        createSeats()
        createBands()
        createFacilities()
        createShows()
    }

}