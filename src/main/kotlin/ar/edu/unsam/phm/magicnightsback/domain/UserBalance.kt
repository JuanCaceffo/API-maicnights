package ar.edu.unsam.phm.magicnightsback.domain

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class BalanceHistory(
    val amount: Double,
    val timeStamp: LocalDateTime
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private var id: Long = 0
}