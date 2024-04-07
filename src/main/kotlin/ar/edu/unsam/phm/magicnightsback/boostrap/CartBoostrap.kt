package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Cart
import ar.edu.unsam.phm.magicnightsback.repository.CartRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component

@Order(4)
@Component
class CartBoostrap(@Autowired val cartRepository: CartRepository, @Autowired val userRepository: UserRepository) :
    InitializingBean {
    val cartPerUser = userRepository.getAll().map { user -> Cart(user)  }

    override fun afterPropertiesSet() {
        println("Comeinzo cartboostrap")
        cartPerUser.forEach { cart -> cartRepository.create(cart) }
        println("finalizacion cartboostrap")
    }
}