package ar.edu.unsam.phm.magicnightsback.service

import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShowDateService(
    private val showDateRepository: ShowDateRepository
) {
    @Transactional(Transactional.TxType.NEVER)
    fun findShoDateByUUID(uuid: UUID): ShowDate? =
        showDateRepository.findById(uuid).getOrNull()

    @Transactional(Transactional.TxType.NEVER)
    fun findShowDateOrErrorByUUID(uuid: UUID): ShowDate =
        findShoDateByUUID(uuid) ?: throw ResponseFindException(FindError.NOT_FOUND(uuid, User::class.stringMe()))
}