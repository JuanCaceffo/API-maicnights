package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.*

@Entity
class Band(
    @Column(length = 40)
    var name: String
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    var cost: Double = 0.0

    constructor(name: String, cost: Double) : this(name) {
        this.cost = cost
    }
}
