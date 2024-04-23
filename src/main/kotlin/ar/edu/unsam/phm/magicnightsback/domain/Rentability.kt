package ar.edu.unsam.phm.magicnightsback.domain

enum class Rentability(val factor: Double) {
    BASE_PRICE(0.8),
    FULL_SALE(1.0),
    MEGA_SHOW(1.3)
}