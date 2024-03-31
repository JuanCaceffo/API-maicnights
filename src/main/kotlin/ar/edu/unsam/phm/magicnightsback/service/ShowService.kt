package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ShowService {
    @Autowired
    lateinit var showDateService: ShowDateService
    @Autowired
    lateinit var showRepository: ShowRepository

    fun getAll(): Iterable<Show> = showRepository.getAll()

//    fun getAvailableDates(showid: Long): Iterable<ShowDate> = showDateService.getAvailable().filter { it.show.id == showid }

}

