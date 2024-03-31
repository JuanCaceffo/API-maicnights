package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class ShowService(
    val showRepo: ShowRepository,
    val userRepo: UserRepository
) {
    fun createShowDate(showId: Int, userId: Int, date:LocalDate) {
        val user = userRepo.getById(userId)
        user.throwIfNotAdmin(UserError.USER_NOT_AUTHORIZED_CREATE_DATE)
        val show = showRepo.getById(showId)
        show.addDate(date)
    }
}
