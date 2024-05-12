//package ar.edu.unsam.phm.magicnightsback.service
//
//import ar.edu.unsam.phm.magicnights.utils.stringMe
//import ar.edu.unsam.phm.magicnightsback.domain.ShowDate
//import ar.edu.unsam.phm.magicnightsback.domain.User
//import ar.edu.unsam.phm.magicnightsback.exceptions.FindError
//import ar.edu.unsam.phm.magicnightsback.exceptions.ResponseFindException
//import ar.edu.unsam.phm.magicnightsback.repository.ShowDateRepository
//import jakarta.transaction.Transactional
//import org.springframework.stereotype.Service
//import java.util.*
//import kotlin.jvm.optionals.getOrNull
//
//@Service
//class ShowDateService(
//    private val showDateRepository: ShowDateRepository
//) {
//    @Transactional(Transactional.TxType.NEVER)
//    fun findById(id: Long): ShowDate? =
//        showDateRepository.findById(id).getOrNull()
//
//    @Transactional(Transactional.TxType.NEVER)
//    fun findByIdOrError(id: Long): ShowDate =
//        findById(id) ?: throw ResponseFindException (FindError.NOT_FOUND(id, User::class.stringMe()))
//}