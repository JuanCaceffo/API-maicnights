package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable
import java.time.LocalDateTime

class Comment (
    val user: User,
    val band: Band,
    val text: String,
    val rating: Int
) : Iterable() {
    val date = LocalDateTime.now()
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}