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

    @EntityGraph(attributePaths = [
        "tickets",
        "tickets.show",
        "tickets.show.band",
        "tickets.show.facility",
        "tickets.show.dates",
        "tickets.show.dates.attendees",
        "friends", "balances"
    ])
    override fun findById(id: Long): Optional<User>

    @Query("""
        SELECT u
        FROM User u
        WHERE u.id IN (
            SELECT u2.id
            FROM User u2
            JOIN u2.tickets t
            GROUP BY u2.id
            HAVING COUNT(t) > :ticketsQuantity
        )
    """)
    fun findUsersWithMoreTicketsThan(ticketsQuantity: Int): Iterable<User>

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