package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.error.UserError
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import ar.edu.unsam.phm.magicnightsback.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime


@Service
class ShowService {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var showRepository: ShowRepository

    fun getAll(): Iterable<Show> = showRepository.getAll()

    fun createShowDate(showId: Long, userId: Long, date: LocalDateTime) {
        userRepository.getById(userId).throwIfNotAdmin(UserError.USER_NOT_AUTHORIZED_CREATE_DATE)
        showRepository.getById(showId).addDate(date)
    }
}

