package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnightsback.dto.ShowDTO
import ar.edu.unsam.phm.magicnightsback.repository.CustomRepository
import ar.edu.unsam.phm.magicnightsback.repository.ShowRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ShowService {
    @Autowired
    lateinit var showRepository: ShowRepository

    fun getAvailable() : List<ShowDTO> = showRepository.getAvailable()

}