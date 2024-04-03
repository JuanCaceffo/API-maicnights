package ar.edu.unsam.phm.magicnightsback.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point
import java.time.LocalDateTime

class ShowDateTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, AllSetTypeNames.valueOf(TheaterSeatType.LOWERLEVEL.name), 10)
    val pullman = SeatType(TheaterSeatType.PULLMAN, AllSetTypeNames.valueOf(TheaterSeatType.PULLMAN.name),10)

    val theterWithFullAvailability = Facility(
        "Teatro GrandRex",
        Point(-34.54612, -58.45004),
        TheaterStrategy()
    ).apply {
        addSeatType(lowerLevel)
        addSeatType(pullman)
    }

    val showBase = Show("La vela puerca", Band("La vela puerca", 10000.0), theterWithFullAvailability)
    describe("Tests ShowDates") {

        it("Un show cualquiera con una sola funcion y todos los asinetos disponibles permite reservar asientos de cualquier ubicacion y los asientos disponibles para dicha fecha en dicha ubicacion y en total se veran disminuidos") {
            //ARRANGE
            val date = LocalDateTime.now().plusDays(10)
            showBase.addDate(date)
            val showDate = showBase.dates.first()
            //ACT
            showDate.reserveSeat(AllSetTypeNames.valueOf(TheaterSeatType.LOWERLEVEL.name), 5)
            showDate.reserveSeat(AllSetTypeNames.valueOf(TheaterSeatType.PULLMAN.name), 3)
            //ASSERT
            showDate.availableSeatsOf(AllSetTypeNames.valueOf(TheaterSeatType.LOWERLEVEL.name)) shouldBe 5
            showDate.availableSeatsOf(AllSetTypeNames.valueOf(TheaterSeatType.PULLMAN.name)) shouldBe 7
            showDate.totalAvailableSeatsOf() shouldBe 12
        }
    }
})