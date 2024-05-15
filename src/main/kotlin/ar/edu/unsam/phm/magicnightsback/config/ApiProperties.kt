package ar.edu.unsam.phm.magicnightsback.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("api")
data class ApiProperties (
    val base: String,
    val ticket: String,
    val user: String,
    val show: String,
    val cart: String,
    val showdate: String,
)