package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.cartError
import ar.edu.unsam.phm.magicnightsback.interfaces.CustomCrudRepository
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import java.util.*

interface CartRepository: CrudRepository<Cart, Long> {
//    fun getCardFor(userId: Long) = getAll().find { cart -> cart.user.id == userId } ?: throw NotFoundException(cartError.CART_FOR_USER_NOT_FOUND)
    override fun findById(id: Long): Optional<Cart>
}