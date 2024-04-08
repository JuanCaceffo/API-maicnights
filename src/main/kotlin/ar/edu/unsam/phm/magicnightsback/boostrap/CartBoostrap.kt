package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Component
@Order(5)
@DependsOn("userBoostrap")
class CartBoostrap(@Autowired val cartRepository: CartRepository, @Autowired val userRepository: UserRepository) :
    InitializingBean {
    val cartPerUser = userRepository.getAll().map { user -> Cart(user)  }

    override fun afterPropertiesSet() {
        println("Cart creation process starts")
        cartPerUser.forEach { cart -> cartRepository.create(cart) }
        println("Cart creation process ends")
    }
}