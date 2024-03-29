package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.domain.Show
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


@Service
class ShowService {
    @Autowired
    lateinit var showDateService: ShowDateService

    fun getAvailable(): Iterable<Show> = showDateService.getAvailable().map { it.show }.toSet()
}

