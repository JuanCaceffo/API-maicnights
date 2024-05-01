package ar.edu.unsam.phm.magicnightsback.factory

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.dto.ShowDateDTO
import io.mockk.every
import org.springframework.stereotype.Component
import java.time.LocalDateTime

enum class UserTypes {
    ADMIN, POOR, NORMAL
}

enum class ShowTypes {
    BIGSHOW, SMALLSHOW, BADSMALLSHOW
}

enum class FacilityTypes {
    STADIUM, THEATER, BADTHEATER
}

@Component
class TestFactory {
    fun createBand() = Band("Test Band", 100000.0)
    fun createSeat(type: SeatTypes) = Seat(type)
    fun createFacility(type: FacilityTypes): Facility {
        return when (type) {
            FacilityTypes.STADIUM -> TestStadium(
                listOf(createSeat(SeatTypes.FIELD), createSeat(SeatTypes.UPPERLEVEL), createSeat(SeatTypes.BOX))
            ).build()

            FacilityTypes.THEATER -> TestTheater(
                listOf(
                    createSeat(SeatTypes.PULLMAN), createSeat(SeatTypes.LOWERLEVEL)
                )
            ).build()

            FacilityTypes.BADTHEATER -> TestBadTheater(
                listOf(
                    createSeat(SeatTypes.PULLMAN), createSeat(SeatTypes.LOWERLEVEL)
                )
            ).build()

        }
    }

    fun createShow(type: ShowTypes) = when (type) {
        ShowTypes.BIGSHOW -> BaseShow(
            ShowTypes.BIGSHOW.name, createBand(), createFacility(FacilityTypes.STADIUM)
        ).build()

        ShowTypes.SMALLSHOW -> BaseShow(
            ShowTypes.SMALLSHOW.name, createBand(), createFacility(FacilityTypes.THEATER)
        ).build()

        ShowTypes.BADSMALLSHOW -> BaseShow(
            ShowTypes.BADSMALLSHOW.name, createBand(), createFacility(FacilityTypes.BADTHEATER)
        ).build()
    }

    fun createUser(type: UserTypes) = when (type) {
        UserTypes.ADMIN -> AdminUser().build()
        UserTypes.POOR -> TODO()
        UserTypes.NORMAL -> CommonUser().build()
    }
}

interface TestObject<T> {
    fun build(): T
}

interface TestShow : TestObject<Show> {
    val name: String
    val band: Band
    val facility: Facility
}

interface TestFacility : TestObject<Facility> {
    val seats: List<Seat>
}

class TestStadium(override val seats: List<Seat>) : TestFacility {
    override fun build(): Stadium = Stadium("Test Stadium", Point(1.0, 1.0), 100000.0).apply {
        addPlace(seats[0], 30000)
        addPlace(seats[1], 20000)
        addPlace(seats[2], 10000)
    }
}

class TestTheater(override val seats: List<Seat>) : TestFacility {
    override fun build(): Theater = Theater("Test Theater", Point(1.0, 1.0)).apply {
        addPlace(seats[0], 3000)
        addPlace(seats[1], 2000)
        hasGoodAcoustics = true
    }
}

class TestBadTheater(override val seats: List<Seat>) : TestFacility {
    override fun build(): Theater = Theater("Test Bad Theater", Point(1.0, 1.0)).apply {
        addPlace(seats[0], 3000)
        addPlace(seats[1], 2000)
    }
}

class BaseShow(override val name: String, override val band: Band, override val facility: Facility) : TestShow {

    override fun build() = Show("Big Show", band, facility)
}

class AdminUser : TestObject<User> {
    override fun build() = User("admin", "admin", "admin", "asdf").apply { isAdmin = true }
}

class CommonUser : TestObject<User> {
    override fun build() = User("admin", "admin", "admin", "asdf")
}