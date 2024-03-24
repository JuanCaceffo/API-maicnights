package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.error.BusinessException
import ar.edu.unsam.phm.magicnightsback.error.showError
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.throwables.shouldThrowWithMessage
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe
import org.uqbar.geodds.Point
import java.time.LocalDate

val upperLevel = SeatType(StadiumSeatType.UPPERLEVEL,300)
val field = SeatType(StadiumSeatType.FIELD,1000)
val box = SeatType(StadiumSeatType.BOX,200)
val lowerLevel = SeatType(TheaterSeatType.LOWERLEVEL,10)
val pullman = SeatType(TheaterSeatType.PULLMAN,10)

val theterWithLowCapacity = Facility (
    "Teatro de pacheco",
    Point(-34.54612, -58.45004),
    TheaterStrategy()
).apply{
    addSeatType(lowerLevel)
    addSeatType(pullman)
}

val showBase = Show("La vela puerca",Band("La vela puerca",10000.0), theterWithLowCapacity)
class ShowTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

//    describe("Tests Shows") {
//        showBase.addDate(LocalDate.of(2023,2,10))
//
//        it("Un show en estado base con un teatro chico con acustica mala y una sola funcion tiene un precio bajo para cada entrada en las diferentes ubicaciones") {
//            showBase.fullTicketPrice(TheaterSeatType.PULLMAN) shouldBe 12400.0
//            showBase.fullTicketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 16400.0
//
//        }
//        it("Un show en estado de venta plena con un teatro chico con acustica mala y una sola funcion tiene un precio promedio para cada entrada en las diferentes ubicaciones") {
//            //ACTIVATE
//            showBase.changeRentability(FullSale())
//            //ASSERT
//            showBase.fullTicketPrice(TheaterSeatType.PULLMAN) shouldBe 15500.0
//            showBase.fullTicketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 20500.0
//
//        }
//        it("Un show en estado de venta MegaShow con un teatro chico con acustica mala y una sola funcion tiene un precio elevado para cada entrada en las diferentes ubicaciones") {
//            //ACTIVATE
//            showBase.changeRentability(MegaShow())
//            //ASSERT
//            showBase.fullTicketPrice(TheaterSeatType.PULLMAN) shouldBe 20150.0
//            showBase.fullTicketPrice(TheaterSeatType.LOWERLEVEL) shouldBe 26650.0
//        }
//        it("Un show cualquiera con una sola funcion y todos los asinetos disponibles permite reservar asientos de cualquier ubicacion y los asientos disponibles para dicha fecha en dicha ubicacion y en total se veran disminuidos") {
//            //ARRANGE
//            val showDate = showBase.dates.first()
//            //ACT
//            showDate.reserveSeat(TheaterSeatType.LOWERLEVEL, 5)
//            showDate.reserveSeat(TheaterSeatType.PULLMAN, 3)
//            //ASSERT
//            showDate.avilableSetsOf(TheaterSeatType.LOWERLEVEL)!! shouldBe 5
//            showDate.avilableSetsOf(TheaterSeatType.PULLMAN)!! shouldBe 7
//            showDate.totalCapacity() shouldBe 12
//        }
//
//        it("Un show cualquiera con una sola funcion al intentar reservar en dicha funcion mas asientos de los que hay disponibles en caulquier ubicacion falla") {
//            //ARRANGE
//            val showDate = showBase.dates.first()
//            //ASSERT
//            shouldThrow<BusinessException> {
//                showDate.reserveSeat(TheaterSeatType.LOWERLEVEL, 11)
//            }.message shouldBe showError.MSG_SETS_UNAVILABLES
//            shouldThrow<BusinessException> {
//                showDate.reserveSeat(TheaterSeatType.PULLMAN, 11)
//            }.message shouldBe showError.MSG_SETS_UNAVILABLES
//        }
//    }
})
