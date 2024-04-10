package ar.edu.unsam.phm.magicnightsback.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point
import java.time.LocalDate
import java.time.LocalDateTime

val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL, 300)
val field = SeatType(StadiumSeatType.FIELD, 1000)
val box = SeatType(StadiumSeatType.BOX,200)
val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
val pullman = SeatType(TheaterSeatType.PULLMAN, 300)

val theterWithLowCapacity = Facility(
    "Teatro de pacheco",
    Point(-34.54612, -58.45004),
    TheaterStrategy()
).apply {
    addSeatType(lowerLevel)
    addSeatType(pullman)
}

val showBase = Show("La vela puerca", Band("La vela puerca", 10000.0), theterWithLowCapacity)


class ShowTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest
    val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL, 300)
    val field = SeatType(StadiumSeatType.FIELD, 1000)
    val box = SeatType(StadiumSeatType.BOX,200)
    val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL, 500)
    val pullman = SeatType(TheaterSeatType.PULLMAN, 300)


    val users = mapOf(
        "Pablo" to User(
            name = "Pablo",
            surname = "Foglia",
            username = "MadEscoces",
            dni = 26765114,
            birthday = LocalDate.of(1978, 10, 20),
            password = "asdf",
            isAdmin = true
        ), "Juan" to User(
            name = "Juan",
            surname = "Caccefo",
            username = "juanceto01",
            dni = 1,
            birthday = LocalDate.of(2003, 2, 1),
            password = "asdf",
            profileImage = ""
        ), "Sol" to User(
            name = "Sol",
            surname = "Lopez",
            username = "mariasol",
            dni = 1,
            birthday = LocalDate.of(2001, 2, 15),
            password = "asdf"
        ), "Denise" to User(
            name = "Denise",
            surname = "Amarfil",
            username = "Denise123",
            dni = 1,
            birthday = LocalDate.of(2001, 5, 15),
            password = "asdf"
        )
    )

    val theterWithLowCapacity = Facility(
        "Teatro de pacheco",
        Point(-34.54612, -58.45004),
        TheaterStrategy()
    ).apply {
        addSeatType(lowerLevel)
        addSeatType(pullman)
    }

    val showBase = Show("La vela puerca", Band("La vela puerca", 10000.0), theterWithLowCapacity)
    val ticketPricePullmanBase = 8110
    val ticketPriceLowerlevelBase = 12110

    describe("Tests Shows") {
        it("Un show en estado base con un teatro chico con acustica mala y una sola funcion tiene un precio bajo para cada entrada en las diferentes ubicaciones") {
            showBase.ticketPrice(TheaterSeatType.PULLMAN) shouldBe ticketPricePullmanBase
            showBase.ticketPrice(TheaterSeatType.LOWERLEVEL) shouldBe ticketPriceLowerlevelBase

        }
        it("Un show en estado de venta plena con un teatro chico con acustica mala y una sola funcion tiene un precio promedio para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(FullSale())
            //ASSERT
            showBase.ticketPrice(TheaterSeatType.PULLMAN) shouldBe (ticketPricePullmanBase * (1/0.8))
            showBase.ticketPrice(TheaterSeatType.LOWERLEVEL) shouldBe (ticketPriceLowerlevelBase * (1/0.8))

        }
        it("Un show en estado de venta MegaShow con un teatro chico con acustica mala y una sola funcion tiene un precio elevado para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(MegaShow())
            //ASSERT
            showBase.ticketPrice(TheaterSeatType.PULLMAN) shouldBe (ticketPricePullmanBase * (1.3/0.8))
            showBase.ticketPrice(TheaterSeatType.LOWERLEVEL) shouldBe (ticketPriceLowerlevelBase * (1.3/0.8))
        }
        it("Debe poder agregar correctamente fechas al show") {
            repeat(3){ showBase.addDate(LocalDateTime.now().plusDays(it.toLong())) }
            showBase.allDates().size shouldBe 3
        }

        it("Debe poder calcular el total de tickets vendidos para un tipo de asiento específico") {
            showBase.addDate(LocalDateTime.now().plusDays(1))
            showBase.dates.first().reserveSeat(TheaterSeatType.PULLMAN, 5)
            showBase.ticketsSoldOfSeatType(TheaterSeatType.PULLMAN) shouldBe 5
        }

        it("Debe poder calcular el total de tickets vendidos para todos los tipos de asientos") {
            showBase.addDate(LocalDateTime.now().plusDays(1))
            showBase.dates.first().reserveSeat(TheaterSeatType.PULLMAN, 5)
            showBase.dates.first().reserveSeat(TheaterSeatType.LOWERLEVEL, 10)
            showBase.totalTicketsSold() shouldBe 15
        }

        it("El Show debe poder devolver el total de attendees incluyendo todas las fechas") {
            repeat(3){ showBase.addDate(LocalDateTime.now().plusDays(it.toLong())) }

            showBase.dates.first().addAttendee(users["Sol"]!!)
            showBase.dates.last().addAttendee(users["Denise"]!!)
            showBase.dates.last().addAttendee(users["Juan"]!!)
            showBase.dates.last().addAttendee(users["Pablo"]!!)

            showBase.allAttendees().size shouldBe 4
            showBase.allAttendees().containsAll(users.values.toList()) shouldBe true
        }

        it("Show con 1 fecha soldOut y otra fecha con seats disponible debe poder informarlo correctamente") {
            repeat(2){ showBase.addDate(LocalDateTime.now().plusDays(it.toLong())) }
            showBase.dates.forEach{
                it.reserveSeat(TheaterSeatType.LOWERLEVEL, 500)
                it.reserveSeat(TheaterSeatType.PULLMAN, 295)
            }
            showBase.dates.last().reserveSeat(TheaterSeatType.PULLMAN, 5)

            showBase.soldOutDates() shouldBe 1
            showBase.dates.first().totalAvailableSeats() shouldBe 5
        }

        it("Show debe devolver correctamente todos los tickets vendidos de todas las fechas") {
            repeat(2){ showBase.addDate(LocalDateTime.now().plusDays(it.toLong())) }

            showBase.dates.first().reserveSeat(TheaterSeatType.LOWERLEVEL, 10)
            showBase.dates.first().reserveSeat(TheaterSeatType.PULLMAN, 5)
            showBase.dates.last().reserveSeat(TheaterSeatType.PULLMAN, 5)

            showBase.totalTicketsSold() shouldBe 20
        }

        it("Show debe devolver correctamente el total vendido de todas las fechas") {
            repeat(2){ showBase.addDate(LocalDateTime.now().plusDays(it.toLong())) }

            showBase.dates.first().reserveSeat(TheaterSeatType.LOWERLEVEL, 500)
            showBase.dates.first().reserveSeat(TheaterSeatType.PULLMAN, 295)
            showBase.dates.last().reserveSeat(TheaterSeatType.PULLMAN, 295)

            showBase.totalSales() shouldBe (ticketPriceLowerlevelBase * 500 + ticketPricePullmanBase * 590)
        }
    }
})
