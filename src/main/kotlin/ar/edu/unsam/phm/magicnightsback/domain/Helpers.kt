package ar.edu.unsam.phm.magicnightsback.domain

import java.time.LocalDate
import java.time.Period

fun LocalDate.calculateAge(): Int = Period.between(this, LocalDate.now()).years