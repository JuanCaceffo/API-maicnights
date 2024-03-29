package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.RepositoryProps

data class Band(val name: String, val cost: Double) : RepositoryProps() {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}