package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface UserRepository : CrudRepository<User, Long>{
    fun findByUsername(username: String): Optional<User>
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