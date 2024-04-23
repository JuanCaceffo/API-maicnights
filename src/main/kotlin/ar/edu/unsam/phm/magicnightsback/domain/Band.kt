package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.*

@Entity
data class Band(
    @Column(length = 40)
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column
    var cost: Double = 0.0
}
