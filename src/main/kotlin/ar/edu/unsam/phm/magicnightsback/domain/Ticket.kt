package ar.edu.unsam.phm.magicnightsback.domain

import java.time.LocalDate

class Ticket(
    val number: Int,
    val seatType: SeatType,
    val date: LocalDate,
    val show: Show
) {

    //fun price() = (show.price / show.availableSeats) + show.seattypeprice(seatType)

}

//class Show (
//var price: Double,
//var availableSeats: Double
//) {

//}