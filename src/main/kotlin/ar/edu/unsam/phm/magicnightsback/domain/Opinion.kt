package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps
import java.time.LocalDateTime

class Opinion (
    val user: User,
    val band: Band,
    val text: String,
    val rating: Int
) : RepositoryProps() {
    val date = LocalDateTime.now()
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}