package ar.edu.unsam.phm.magicnightsback.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point

class ShowTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL,AllSetTypeNames.valueOf(StadiumSeatType.UPPERLEVEL.name), 300)
    val field = SeatType(StadiumSeatType.FIELD,AllSetTypeNames.valueOf(StadiumSeatType.FIELD.name) , 1000)
    val box = SeatType(StadiumSeatType.BOX,AllSetTypeNames.valueOf(StadiumSeatType.BOX.name) ,200)
    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL,AllSetTypeNames.valueOf(TheaterSeatType.LOWERLEVEL.name), 500)
    val pullman = SeatType(TheaterSeatType.PULLMAN, AllSetTypeNames.valueOf(TheaterSeatType.PULLMAN.name),300)


    val theterWithLowCapacity = Facility(
        "Teatro de pacheco",
        Point(-34.54612, -58.45004),
        TheaterStrategy()
    ).apply {
        addSeatType(lowerLevel)
        addSeatType(pullman)
    }

    val showBase = Show("La vela puerca", Band("La vela puerca", 10000.0), theterWithLowCapacity)

    //cost pullman 10137.5
    //cost lowerllevel 15137.5
    describe("Tests Shows") {
        it("Un show en estado base con un teatro chico con acustica mala y una sola funcion tiene un precio bajo para cada entrada en las diferentes ubicaciones") {
            showBase.ticketPrice(TheaterSeatType.PULLMAN) shouldBe 8110
            showBase.ticketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 12110

        }
        it("Un show en estado de venta plena con un teatro chico con acustica mala y una sola funcion tiene un precio promedio para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(FullSale())
            //ASSERT
            showBase.ticketPrice(TheaterSeatType.PULLMAN) shouldBe 10137.5
            showBase.ticketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 15137.5

        }
        it("Un show en estado de venta MegaShow con un teatro chico con acustica mala y una sola funcion tiene un precio elevado para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(MegaShow())
            //ASSERT
            showBase.ticketPrice(TheaterSeatType.PULLMAN) shouldBe 13178.75
            showBase.ticketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 19678.75
        }
    }
})
