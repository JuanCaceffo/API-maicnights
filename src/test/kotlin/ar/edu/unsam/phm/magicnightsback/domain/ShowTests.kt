package ar.edu.unsam.phm.magicnightsback.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point

class ShowTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL,300)
    val field = SeatType(StadiumSeatType.FIELD,1000)
    val box = SeatType(StadiumSeatType.BOX,200)
    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL,10)
    val pullman = SeatType(TheaterSeatType.PULLMAN,10)

    val theterWithLowCapacity = Facility (
        "Teatro de pacheco",
        Point(-34.54612, -58.45004),
        TheaterStrategy()
    ).apply{
        addSeatType(lowerLevel)
        addSeatType(pullman)
    }

    val showBase = Show("La vela puerca",Band("La vela puerca",10000.0), theterWithLowCapacity)

    describe("Tests Shows") {

        it("Un show en estado base con un teatro chico con acustica mala y una sola funcion tiene un precio bajo para cada entrada en las diferentes ubicaciones") {
            showBase.fullTicketPrice(TheaterSeatType.PULLMAN) shouldBe 14400.0
            showBase.fullTicketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 19400.0

        }
        it("Un show en estado de venta plena con un teatro chico con acustica mala y una sola funcion tiene un precio promedio para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(FullSale())
            //ASSERT
            showBase.fullTicketPrice(TheaterSeatType.PULLMAN) shouldBe 15500.0
            showBase.fullTicketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 20500.0

        }
        it("Un show en estado de venta MegaShow con un teatro chico con acustica mala y una sola funcion tiene un precio elevado para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(MegaShow())
            //ASSERT
            showBase.fullTicketPrice(TheaterSeatType.PULLMAN) shouldBe 17150.0
            showBase.fullTicketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 22150.0
        }
    }
})
