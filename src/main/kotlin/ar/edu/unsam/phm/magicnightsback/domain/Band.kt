package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class Band {
    @Id
    @GeneratedValue
    var id: Long? = null

    @Column(length = 40)
    var name: String = ""

    @Column
    var cost: Double = 0.0
}
