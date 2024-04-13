//package ar.edu.unsam.phm.magicnightsback.repository
//
//import ar.edu.unsam.phm.magicnightsback.domain.Cart
//import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
//import ar.edu.unsam.phm.magicnightsback.error.cartError
//import org.springframework.stereotype.Repository
//
//@Repository
//class CartRepository: CustomRepository<Cart>() {
//
//    fun getCardFor(userId: Long) = getAll().find { cart -> cart.user.id == userId } ?: throw NotFoundException(cartError.CART_FOR_USER_NOT_FOUND)
//}