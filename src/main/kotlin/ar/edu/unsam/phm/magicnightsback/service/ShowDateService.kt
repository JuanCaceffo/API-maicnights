package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
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
    lateinit var showDateRepository: ShowDateRepository
    @Autowired
    lateinit var showRepository: ShowRepository
    @Autowired
    lateinit var userRepository: UserRepository

    fun allShowDatesOf(showid: Long) = showDateRepository.getAll().filter { it.show.id == showid }

    fun addShowDate(date: LocalDateTime, show: Show){
        showDateRepository.create(ShowDate(date, show))
    }

    fun createShowDate(showId: Long, userId: Long, date:LocalDateTime) {
        userRepository.getById(userId).throwIfNotAdmin(UserError.USER_NOT_AUTHORIZED_CREATE_DATE)
        val show = showRepository.getById(showId)
        val showDate = ShowDate(date,show)
        showDateRepository.create(showDate)
        show.addDate(showDate.id)
    }
}