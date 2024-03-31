package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.serializers.LoginUserDTO
import org.springframework.stereotype.Repository

@Repository
class UserRepository : CustomRepository<User>() {
    fun getLoginUser(loginUser: LoginUserDTO): Int? {
        // Dado un usuario de tipo LoginDTO, devuelve el usuario encontrado en el repositorio que con el
        // nommbre de usuario y contraseña si existe.
        return this.elements.entries.find {
            it.value.username == loginUser.username && it.value.password == loginUser.password
        }?.key
    }

    fun addCredit(id: Int, creditToAdd: Double) {
        val user = this.getById(id)
        user.credit += creditToAdd

        this.update(user)
    }
}