package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.UserBalanceDTO
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @EntityGraph(attributePaths = ["tickets"])
    fun findByUsername(username: String): Optional<User>

    @EntityGraph(attributePaths = ["friends"])
    override fun findById(id: Long): Optional<User>

    fun getBalances(): UserBalanceDTO {
        TODO("Not yet implemented")
    }
}



//    fun getLoginUser(loginUser: LoginUserDTO): Long? {
//        // Dado un usuario de tipo LoginDTO, devuelve el usuario encontrado en el repositorio que con el
//        // nommbre de usuario y contraseña si existe.
//        return this.elements.entries.find {
//            it.value.username == loginUser.username && it.value.password == loginUser.password
//        }?.key
//    }
//
//    fun addCredit(id: Long, creditToAdd: Double) {
//        val user = this.getById(id)
//        user.credit += creditToAdd
//
//        this.update(user)
//    }
//
//    fun getFriends(userId: Long): MutableList<User> {
//        return this.getById(userId).friends
//    }
//}