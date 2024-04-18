package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.*
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.SeatRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import kotlin.jvm.optionals.getOrNull

@Component
@Order(4)
@DependsOn("seatBootstrap")
class CartBoostrap(
    val cartRepository: CartRepository,
    userBoostrap: UserBoostrap,
    showBoostrap: ShowBoostrap,
    seatRepository: SeatRepository
) : InitializingBean {

//    val smallshowGranrex = showBoostrap.shows[0]
//    val smallShowTeatroColon = showBoostrap.shows[1]
//    val bigShowRiver = showBoostrap.shows[2]
//    val bigShowLaBombonera = showBoostrap.shows[3]
//    val bestSmallShowMovistarArena = showBoostrap.shows[4]
//    val bestSmallShowTeatroOpera = showBoostrap.shows[5]
//    val losRedondosClubDePolo = showBoostrap.shows[6]
//
//    val pullman = seatRepository.findByName(SeatTypes.PULLMAN.name).get()
//    val upperlevel = seatRepository.findByName(SeatTypes.UPPERLEVEL.name).get()
//    val lowerlevel = seatRepository.findByName(SeatTypes.LOWERLEVEL.name).get()
//    val field = seatRepository.findByName(SeatTypes.FIELD.name).get()


    val carts = mapOf(
        "CartPablo" to Cart(userBoostrap.users[0]),
        "CartSol" to Cart(userBoostrap.users[1]),
        "CartJuan" to Cart(userBoostrap.users[2]),
        "CartDenise" to Cart(userBoostrap.users[3]),
        "CartCarolina" to Cart(userBoostrap.users[4]),
        "CartMarcos" to Cart(userBoostrap.users[5]),
        "CartAna" to Cart(userBoostrap.users[6])
    )

    fun createCarts() {
        carts.values.forEach {
            val cartInRepo = cartRepository.findByUserId(it.user.id!!).getOrNull()
            if (cartInRepo != null) {
                it.id = cartInRepo.id
            } else {
                cartRepository.save(it)
                println("Cart for ${it.user.name} created")
            }
        }
    }

//    fun addTickets(){
//        carts["CartPablo"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    pullman,
//                    smallshowGranrex.ticketPrice(pullman)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    smallShowTeatroColon,
//                    smallShowTeatroColon.dates.elementAt(1),
//                    pullman,
//                    smallShowTeatroColon.ticketPrice(pullman),
//                    2
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bigShowRiver,
//                    bigShowRiver.dates.elementAt(0),
//                    upperlevel,
//                    bigShowRiver.ticketPrice(upperlevel),
//                    2
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bigShowLaBombonera,
//                    bigShowLaBombonera.dates.elementAt(0),
//                    upperlevel,
//                    bigShowLaBombonera.ticketPrice(upperlevel)
//                )
//            )
//        }
//        carts["CartSol"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    lowerlevel,
//                    smallshowGranrex.ticketPrice(lowerlevel)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bigShowRiver,
//                    bigShowRiver.dates.elementAt(0),
//                    upperlevel,
//                    bigShowRiver.ticketPrice(upperlevel),
//                    2
//                )
//            )
//        }
//        carts["CartJuan"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    pullman,
//                    smallshowGranrex.ticketPrice(pullman)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bigShowRiver,
//                    bigShowRiver.dates.elementAt(0),
//                    upperlevel,
//                    bigShowRiver.ticketPrice(upperlevel),
//                    2
//                )
//            )
//        }
//        carts["CartDenise"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    pullman,
//                    smallshowGranrex.ticketPrice(pullman)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bigShowRiver,
//                    bigShowRiver.dates.elementAt(0),
//                    upperlevel,
//                    bigShowRiver.ticketPrice(upperlevel),
//                    2
//                )
//            )
//        }
//        carts["CartCarolina"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    pullman,
//                    smallshowGranrex.ticketPrice(pullman)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bestSmallShowMovistarArena,
//                    bestSmallShowMovistarArena.dates.elementAt(0),
//                    lowerlevel,
//                    bestSmallShowMovistarArena.ticketPrice(lowerlevel)
//                )
//            )
//        }
//        carts["CartMarcos"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    pullman,
//                    smallshowGranrex.ticketPrice(pullman)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    bestSmallShowTeatroOpera,
//                    bestSmallShowTeatroOpera.dates.elementAt(0),
//                    pullman,
//                    bestSmallShowTeatroOpera.ticketPrice(pullman)
//                )
//            )
//        }
//        carts["CartAna"]!!.apply {
//            reserveTicket(
//                Ticket(
//                    smallshowGranrex,
//                    smallshowGranrex.dates.elementAt(0),
//                    pullman,
//                    smallshowGranrex.ticketPrice(pullman)
//                )
//            )
//            reserveTicket(
//                Ticket(
//                    losRedondosClubDePolo,
//                    losRedondosClubDePolo.dates.elementAt(0),
//                    field,
//                    losRedondosClubDePolo.ticketPrice(field)
//                )
//            )
//        }
//    }
//
//    fun buyCarts() {
//        carts.values.forEach {
//            it.buyReservedTickets()
//        }
//    }

    override fun afterPropertiesSet() {
        println("Cart creation process starts")
//        addTickets()
//        buyCarts()
        createCarts()
        println("Cart creation process ends")
    }
}