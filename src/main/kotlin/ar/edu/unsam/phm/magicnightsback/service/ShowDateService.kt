package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Facility
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class ShowDateService {
    @Autowired
    lateinit var showDateRepository: ShowDateRepository

    fun addShowDate(date: LocalDateTime, facility: Facility){
        showDateRepository.create(ShowDate(date, facility))
    }
}