package ar.edu.unsam.phm.magicnightsback.repository

import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.UserBalanceDTO
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : CrudRepository<User, Long> {
    @EntityGraph(attributePaths = ["tickets", "friends", "balances"])
    fun findByUsername(username: String): Optional<User>

    @EntityGraph(attributePaths = ["friends"])
    override fun findById(id: Long): Optional<User>

    @Query(
        """SELECT
            u.id AS id,
            bh.amount AS amount,
            bh.timeStamp AS timeStamp
        FROM User u
        INNER JOIN u.balances bh
        WHERE bh.user.id = :id
        ORDER BY bh.id DESC  
        LIMIT 2
        """
    )
    fun allBalances(@Param("id") id: Long): List<UserBalanceDTO>
}

//"""SELECT
//            s.id,
//            bh.amount,
//            bh.time_stamp
//        FROM spectator s
//        JOIN balance_history bh ON s.id = bh.user_id
//        WHERE s.id = :id
//        ORDER BY bh.time_stamp
//        LIMIT 2
//        """, nativeQuery = true


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