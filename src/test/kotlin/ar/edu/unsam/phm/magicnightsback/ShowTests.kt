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
            val facility = Theater("TheaterName", Location(0.0, 0.0), 100, 50, hasGoodAcoustics = true)
            val tour = Tour("TourName", band, facility, 2)

            tour.cost() shouldBe 120080.0
        }

        it("nameOfEvent should return the correct name for Tour") {
            val band = Band("BandName", 100.0)
            val facility = Stadium("StadiumName", Location(0.0, 0.0), 100, 50, 25, 250000.0)
            val tour = Tour("TourName", band,facility , 3)

            tour.nameOfEvent shouldBe "TourName"
        }
    }

    describe("Tests Show with Concert") {
        it("costOfTheShow should calculate the correct cost for Concert") {
            val band = Band("BandName", 100.0)
            val facility = Stadium("StadiumName", Location(0.0, 0.0), 100, 50, 25, 250000.0)
            val concert = Concert("ConcertName", band,facility , 1)

            concert.cost() shouldBe 200080.0
        }

        it("nameOfEvent should return the correct name for Concert") {
            val band = Band("BandName", 100.0)
            val facility = Stadium("StadiumName", Location(0.0, 0.0), 100, 50, 25, 250000.0)
            val concert = Concert("ConcertName", band,facility , 0)

            concert.nameOfEvent shouldBe "ConcertName"
        }
    }
})
