package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.domain.enums.Rentability
import ar.edu.unsam.phm.magicnightsback.domain.factory.BandFactory
import ar.edu.unsam.phm.magicnightsback.domain.factory.BandFactoryTypes
import ar.edu.unsam.phm.magicnightsback.domain.factory.FacilityFactory
import ar.edu.unsam.phm.magicnightsback.domain.factory.FacilityFactoryTypes
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe

class ShowTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Show Tests") {
        //Arrange
        val vela = BandFactory().createFacility(BandFactoryTypes.CHEAP)

        val theater = FacilityFactory().createFacility(FacilityFactoryTypes.BADTHEATER)

        val show = Show(name = "Cachenged", vela, theater)

        it("A show has a base cost") {
            show.cost shouldBe 150000.0
        }

        it("A show can calaculate total capacity") {
            show.totalCapacity() shouldBe 4000
        }

        it("The show returns a base ticket price") {
            show.currentTicketPrice(theater.seats.elementAt(0)) shouldBe 18067.5
        }

        it("The show returns a greater seat price based on a seat type if rentability changes") {
            //Act
            show.changeRentability(Rentability.MEGA_SHOW)
            //Assert
            show.currentTicketPrice(theater.seats.elementAt(0)) shouldBe 23086.25
        }

        it("The show returns all prices") {
            //Assert
            show.allTicketPrices().size shouldBeExactly 2
            show.allTicketPrices() shouldBe listOf(18067.5, 27067.5)
        }

    }
})