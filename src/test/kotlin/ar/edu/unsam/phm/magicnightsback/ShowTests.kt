package ar.edu.unsam.phm.magicnightsback

import ar.edu.unsam.phm.magicnightsback.domain.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ShowTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests Show with Tour") {
        it("costOfTheShow should calculate the correct cost for Tour") {
            val band = Band("BandName", 100.0)
            val facility = Theater("TheaterName", Location(0.0, 0.0), setOf(LowerLevelSeating(100), Pullman(50)), hasGoodAcoustics = true)
            val tour = Tour("TourName", band, facility, 2)

            tour.cost() shouldBe 120080.0
        }

        it("nameOfEvent should return the correct name for Tour") {
            val band = Band("BandName", 100.0)
            val facility = Stadium("StadiumName", Location(0.0, 0.0), setOf(UpperLevelSeating(100), Field(50), Box(25)), 250000.0)
            val tour = Tour("TourName", band, facility, 3)

            tour.nameOfEvent shouldBe "TourName"
        }
    }

    describe("Tests Show with Concert") {
        it("costOfTheShow should calculate the correct cost for Concert") {
            val band = Band("BandName", 100.0)
            val facility = Stadium("StadiumName", Location(0.0, 0.0),setOf(UpperLevelSeating(100), Field(50), Box(25)), 250000.0)
            val concert = Concert("ConcertName", band, facility, 1)

            concert.cost() shouldBe 200080.0
        }

        it("nameOfEvent should return the correct name for Concert") {
            val band = Band("BandName", 100.0)
            val facility = Stadium("StadiumName", Location(0.0, 0.0),setOf(UpperLevelSeating(100), Field(50), Box(25)), 250000.0)
            val concert = Concert("ConcertName", band, facility, 0)

            concert.nameOfEvent shouldBe "ConcertName"
        }
    }

    describe("Rentability show test") {
        val band = Band(name = "ACDC", cost = 40000.0)
        val facility = Theater("Grand Rex", Location(0.0, 0.0), setOf(LowerLevelSeating(100), Pullman(50)), hasGoodAcoustics = true)
        val tour = Tour("TourName", band, facility, 2)
        it("Los shows inician en un precio base donde su rentabilidad es reducida") {
            //Arrage
            //Act
            //Asert
            tour.cost().shouldBe(152000.0)
        }
        it("Un show en que se considera venta plena tiene una rentabilidad normal") {
            //Arrage
            //Act
            tour.changeRentability(FullSale())
            //assert
            tour.cost().shouldBe(190000.0)
        }
        it("Un megashow tiene una rentabilidad mejorada") {
            //Arrage
            //Act
            tour.changeRentability(MegaShow())
            //Assert
            tour.cost().shouldBe(247000.0)
        }
    }
})
