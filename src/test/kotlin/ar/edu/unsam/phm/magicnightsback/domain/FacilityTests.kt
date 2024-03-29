package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point

class FacilityTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Tests Stadium") {
        val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL, 300)
        val field = SeatType(StadiumSeatType.FIELD, 1000)
        val box = SeatType(StadiumSeatType.BOX, 200)
        val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
        val pullman = SeatType(TheaterSeatType.PULLMAN, 300)

        val stadium = Facility(
            name = "River Plate",
            location = Point(-34.54612, -58.45004),
            seatStrategy = StadiumStrategy(10000.0)
        ).apply {
            addSeatType(upperLevel)
            addSeatType(field)
            addSeatType(box)
        }

        it("El metodo fixedCost devuelve el costo fijo pasado como parametro en el contructor de Stadium") {
            stadium.cost() shouldBe 10000.0

        }

        it("El metodo getSeatCapacity devuelve la capacidad del tipo de asiento que recibe como parametro") {
            stadium.getSeatCapacity(StadiumSeatType.FIELD) shouldBe 1000
        }

        it("El metodo getSeatCapacity devuelve la suma de capacidades de todos los tipos de asientos") {
            stadium.getTotalSeatCapacity() shouldBe 1500
        }
    }
    describe("Tests Theater") {


        it("Un teatro con mala acustica tiene un costo fijo de 100000") {
            val theater = Facility(
                "GranRex",
                Point(-34.54612, -58.45004),
                TheaterStrategy()
            )
            theater.seatStrategy.totalCost() shouldBe 100000.0
        }

        it("Un teatro con buena acustica tiene un costo fijo de 150000") {
            val theater = Facility(
                "GranRex",
                Point(-34.54612, -58.45004),
                TheaterStrategy(hasGoodAcoustics = true)
            )
            theater.seatStrategy.totalCost() shouldBe 150000.0
        }
    }
})