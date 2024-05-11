package ar.edu.unsam.phm.magicnightsback.service

import ar.edu.unsam.phm.magicnights.utils.stringMe
import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
import ar.edu.unsam.phm.magicnightsback.domain.User
import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class ShowDateService(
    private val showDateRepository: ShowDateRepository
) {
    @Transactional(Transactional.TxType.NEVER)
    fun findShoDateByUUID(uuid: Long): ShowDate? =
        showDateRepository.findById(uuid).getOrNull()

    @Transactional(Transactional.TxType.NEVER)
    fun findShowDateOrErrorByUUID(uuid: Long): ShowDate =
        findShoDateByUUID(uuid) ?: throw ResponseFindException (FindError.NOT_FOUND(uuid, User::class.stringMe()))
}