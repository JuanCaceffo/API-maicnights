package ar.edu.unsam.phm.magicnightsback

import ar.edu.unsam.phm.magicnightsback.domain.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class FacilityTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Tests Stadium") {
        val stadiumCost = 250000.0
        val upperLevelSeating = UpperLevelSeating(100)
        val field = Field(50)
        val box = Box(25)

        val aStadium = Stadium(
            "a stadium",
            Location(0.0, 0.0),
            setOf(UpperLevelSeating(100), Field(50), box),
            stadiumCost
        )

        it("El metodo fixedCost devuelve el costo fijo pasado como parametro en el contructor de Stadium") {
            aStadium.fixedCost() shouldBe stadiumCost

        }

        it("El metodo getSeatPrice devuelve el precio del tipo de asiento que recibe como parametro") {
            aStadium.getSeatPrice("Box") shouldBe box.price()
        }

        it("El metodo getSeatCapacity devuelve la capacidad del tipo de asiento que recibe como parametro") {
            aStadium.getSeatCapacity("Box") shouldBe box.capacity
        }

        it("El metodo getSeatCapacity devuelve la suma de capacidades de todos los tipos de asientos " +
                "cuando no recibe parametro") {
            val totalCapacity = upperLevelSeating.capacity + field.capacity + box.capacity

            aStadium.getSeatCapacity() shouldBe totalCapacity
        }
    }
    describe("Tests Theater")  {
        it("Un teatro con mala acustica tiene un costo fijo de 100000") {
            val aTheater = Theater(
                "a theater",
                Location(0.0,0.0),
                setOf(LowerLevelSeating(100), Pullman(50))
            )

            aTheater.fixedCost() shouldBe 100000.0
        }

        it("Un teatro con buena acustica tiene un costo fijo de 150000") {
            val aTheater = Theater(
                "a theater",
                Location(0.0,0.0),
                setOf(LowerLevelSeating(100), Pullman(50)),
                true
            )

            aTheater.fixedCost() shouldBe 150000.0
        }

        it("El metodo getSeatPrice devuelve el precio del tipo de asiento que recibe como parametro") {
            val pullman = Pullman(50)

            val aTheater = Theater(
                "a theater",
                Location(0.0,0.0),
                setOf(LowerLevelSeating(100), pullman),
                true
            )

            aTheater.getSeatPrice("Pullman") shouldBe pullman.price()

        }
    }
})