package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ShowService{
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var showRepository: ShowRepository

    fun getAvailable() : Iterable<Show> = showRepository.getAll().filter { it.hasAvailableDates() }

    fun createShowDate(showId: Long, userId: Long, date:LocalDateTime) {
        val user = userRepository.getById(userId)
        user.throwIfNotAdmin(UserError.USER_NOT_AUTHORIZED_CREATE_DATE)
        val show = showRepository.getById(showId)
        show.addDate(date)
    }
}

