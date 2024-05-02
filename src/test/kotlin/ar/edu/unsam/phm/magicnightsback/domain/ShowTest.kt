package ar.edu.unsam.phm.magicnightsback.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ShowTest: DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    describe("Show Tests") {
        //Arrange
        val vela = Band(name = "La Vela Puerca").apply {
            cost = 10000.0
        }

        val theater = Theater(
            name = "Gran Rex",
            location = Point(latitude = -34.603542, longitude = -58.378856)
        ).apply {
            hasGoodAcoustics = true
            addPlace(SeatTypes.PULLMAN, 2000)
            addPlace(SeatTypes.LOWERLEVEL, 3000)
        }

        val show = Show(name = "Cachenged", vela, theater)

        it("A show has a base cost"){
            show.baseCost() shouldBe 160000.0
        }

        it( "The show returns a base ticket price"){
            show.baseTicketPrice(SeatTypes.PULLMAN) shouldBe 15032.0
        }

        it ("The show returns a seat price based on a seat type" ){
            show.ticketPrice(SeatTypes.PULLMAN) shouldBe 12025.6
        }

        it ( "The show returns a greater seat price based on a seat type if rentability changes"){
            //Act
            show.changeRentability(Rentability.MEGA_SHOW)
            //Assert
            show.ticketPrice(SeatTypes.PULLMAN) shouldBe 19541.60
        }
    }
})