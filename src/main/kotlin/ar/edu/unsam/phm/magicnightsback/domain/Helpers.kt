package ar.edu.unsam.phm.magicnightsback.domain

import java.time.LocalDate
import java.time.Period


object ageCalculator {
  fun calculate(birthday: LocalDate):Int = Period.between(birthday, LocalDate.now()).years
}