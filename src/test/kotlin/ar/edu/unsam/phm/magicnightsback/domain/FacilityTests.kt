package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.FacilityError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.doubles.shouldBeLessThan
import io.kotest.matchers.shouldBe

class FacilityTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Tests Stadium") {
        //Arrange
        val upper = Seat(SeatTypes.UPPERLEVEL)
        val box = Seat(SeatTypes.BOX)
        val field = Seat(SeatTypes.FIELD)

        val stadium = Stadium(
            name = "River Plate",
            location = ar.edu.unsam.phm.magicnightsback.domain.Point(-34.54612, -58.45004),
            1000000.0
        )
            .apply {
                addPlace(upper, 20000)
                addPlace(box, 15000)
                addPlace(field, 35000)
            }

        it("Should return the total cost of the facility") {
            //Assert
            stadium.cost() shouldBe 1000000.0
        }

        it("Should return total seat capacy by seat type ") {
            //Assert
            stadium.getPlaceCapacity(SeatTypes.BOX) shouldBe 15000
        }

        it("El metodo getSeatCapacity devuelve la suma de capacidades de todos los tipos de asientos") {
            //Assert
            stadium.getTotalSeatCapacity() shouldBe 70000
        }

        it("Should don't have a cost variant") {
            //Assert
            stadium.fixedCostVariant() shouldBe 0
        }

        it("Fixed price shouldn't be negative") {
            shouldThrow<BusinessException> {
                Stadium(
                    name = "River Plate",
                    location = ar.edu.unsam.phm.magicnightsback.domain.Point(-34.54612, -58.45004),
                    fixedPrice = -1000000.0
                )
            }.message shouldBe FacilityError.NEGATIVE_PRICE
        }
    }

    describe("Tests Theater") {
        //Arrange
        val lower = Seat(SeatTypes.LOWERLEVEL)
        val pullman = Seat(SeatTypes.PULLMAN)
        val theater = Theater(
            name = "Grand Rex",
            Point(-34.54612, -58.45004)
        )
            .apply {
                addPlace(pullman, 10000)
                addPlace(lower, 15000)
            }

        val goodTheater = Theater(
            name = "Coliseo",
            Point(-34.54612, -58.45004)
        )
            .apply {
                addPlace(pullman, 10000)
                addPlace(lower, 15000)
                hasGoodAcoustics = true
            }

        it("A theater with bad acoustics has no cost variant") {
            //Assert
            theater.fixedCostVariant() shouldBe 0.0
        }

        it("A theater with good acoustics has a cost variant") {
            //Assert
            goodTheater.fixedCostVariant() shouldBe 50000.0
        }

        it("The total cost of a theater with good acoustics exceeds that of one with poor acoustics.") {
            //Assert
            theater.cost() shouldBeLessThan (goodTheater.cost())
        }
    }
})