package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
data class Band(
    @Column(length = 40)
    var name: String
) {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column
    var cost: Double = 0.0
}
