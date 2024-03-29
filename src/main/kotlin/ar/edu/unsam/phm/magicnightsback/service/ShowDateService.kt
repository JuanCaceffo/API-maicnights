package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ShowDateService {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var showRepository: ShowRepository

    @Autowired
    lateinit var showDateRepository: ShowDateRepository

    fun getAvailable() = showDateRepository.getAll().filter { !it.datePassed() }

    fun createShowDate(showId: Long, userId: Long, date: LocalDateTime) {
        val user = userRepository.getById(userId)
        user.throwIfNotAdmin(UserError.USER_NOT_AUTHORIZED_CREATE_DATE)
        val show = showRepository.getById(showId)
        showDateRepository.create(ShowDate(date, show))
    }
}