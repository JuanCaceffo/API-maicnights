package ar.edu.unsam.phm.magicnightsback

import ar.edu.unsam.phm.magicnightsback.domain.Location
import ar.edu.unsam.phm.magicnightsback.domain.SeatTypes
import ar.edu.unsam.phm.magicnightsback.domain.Stadium
import ar.edu.unsam.phm.magicnightsback.domain.Theater
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class FacilityTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Tests Stadium") {
        val stadiumCost = 250000.0

        val aStadium = Stadium(
            "a stadium",
            Location(0.0, 0.0),
            mutableMapOf(
                SeatTypes.UPPERLEVEL to 25,
                SeatTypes.FIELD to 50,
                SeatTypes.BOX to 25
            ),
            stadiumCost
        )

        it("El metodo fixedCost devuelve el costo fijo pasado como parametro en el contructor de Stadium") {
            aStadium.fixedCost() shouldBe stadiumCost

    }
    describe("Tests Theater")  {
        it("Un teatro con mala acustica tiene un costo fijo de 100000") {
            val aTheater = Theater(
                "a theater",
                Location(0.0,0.0),
                mutableMapOf(
                    SeatTypes.LOWERLEVEL to 50,
                    SeatTypes.PULLMAN to 50,
                ),
            )

            aTheater.fixedCost() shouldBe 100000.0
        }

        it("Un teatro con buena acustica tiene un costo fijo de 150000") {
            val aTheater = Theater(
                "a theater",
                Location(0.0,0.0),
                mutableMapOf(
                    SeatTypes.LOWERLEVEL to 50,
                    SeatTypes.PULLMAN to 50,
                ),
                true
            )

            aTheater.fixedCost() shouldBe 150000.0
        }
    }
}
})