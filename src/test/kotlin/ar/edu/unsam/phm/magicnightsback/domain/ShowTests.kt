package ar.edu.unsam.phm.magicnightsback.domain

import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import org.uqbar.geodds.Point
import java.time.LocalDate


val theterWithLowCapacity = Theater(
    "Teatro de pacheco",
    Point(-34.45690319910218, -58.63340014585352),
    mutableMapOf(
        SeatTypes.LOWERLEVEL to 10,
        SeatTypes.PULLMAN to 10
    )
)

val showBase = Show("La vela puerca",Band("La vela puerca",10000.0), theterWithLowCapacity)
class ShowTests : DescribeSpec({
    isolationMode = IsolationMode.InstancePerTest

    describe("Tests Shows") {
        showBase.addDate(LocalDate.of(2023,2,10))

        it("Un show en estado base con un teatro chico con acustica mala y una sola funcion tiene un precio bajo para cada entrada en las diferentes ubicaciones") {
            showBase.fullTicketPrice(SeatTypes.PULLMAN).equals(12400.0)
            showBase.fullTicketPrice(SeatTypes.LOWERLEVEL).equals(16400.0)

        }
        it("Un show en estado de venta plena con un teatro chico con acustica mala y una sola funcion tiene un precio promedio para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(FullSale())
            //ASSERT
            showBase.fullTicketPrice(SeatTypes.PULLMAN).equals(15500.0)
            showBase.fullTicketPrice(SeatTypes.LOWERLEVEL).equals(20500.0)

        }
        it("Un show en estado de venta MegaShow con un teatro chico con acustica mala y una sola funcion tiene un precio elevado para cada entrada en las diferentes ubicaciones") {
            //ACTIVATE
            showBase.changeRentability(MegaShow())
            //ASSERT
            showBase.fullTicketPrice(SeatTypes.PULLMAN).equals(20150.0)
            showBase.fullTicketPrice(SeatTypes.LOWERLEVEL).equals(26650.0)
        }
        it("Un show cualquiera con una sola funcion y todos los asinetos disponibles permite reservar asientos de cualquier ubicacion y los asientos disponibles para dicha fecha en dicha ubicacion y en total se veran disminuidos") {
            //ARRANGE
            val showDate = showBase.dates.first()
            //ACT
            showDate.reserveSeat(SeatTypes.LOWERLEVEL, 5)
            showDate.reserveSeat(SeatTypes.PULLMAN, 3)
            //ASSERT
            showDate.avilableSetsOf(SeatTypes.LOWERLEVEL)!!.equals(5)
            showDate.avilableSetsOf(SeatTypes.PULLMAN)!!.equals(7)
            showDate.totalCapacity().equals(12)
        }

    }
})
