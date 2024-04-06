package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.dto.allCommentsDTO
import ar.edu.unsam.phm.magicnightsback.error.NotFoundException
import ar.edu.unsam.phm.magicnightsback.error.RepositoryError
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

    fun getById(id: Long) = showRepository.getById(id)

    fun getAPossibleUserById(userId: Long) = if (userId > -1) userRepository.getById(userId) else null
}

