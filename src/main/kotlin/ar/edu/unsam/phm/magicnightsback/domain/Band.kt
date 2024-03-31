package ar.edu.unsam.phm.magicnightsback.domain

import ar.edu.unsam.phm.magicnightsback.repository.Iterable

data class Band(val name: String, val cost: Double) : Iterable() {
    override fun validSearchCondition(value: String): Boolean {
        TODO("Not yet implemented")
    }
}