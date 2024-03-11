package ar.edu.unsam.phm.magicnightsback.boostrap

import ar.edu.unsam.phm.magicnightsback.domain.Location
import ar.edu.unsam.phm.magicnightsback.domain.Stadium
import ar.edu.unsam.phm.magicnightsback.domain.Theater
import ar.edu.unsam.phm.magicnightsback.repository.FacilityRepository
import org.springframework.beans.factory.InitializingBean
import org.springframework.stereotype.Component

@Component
class FacilityBoostrap(
  val facilityRepository: FacilityRepository
) : InitializingBean {
  private val facilities = mapOf(
    "GranRex" to Theater(
      name = "Gran Rex",
      location = Location(latitud = 0.1, longitud = 0.1),
      lowerLevelSeatingCapacity = 10,
      pullmanCapacity = 10
    ), "River" to Stadium(
      name = "River Plate",
      location = Location(latitud = 0.1, longitud = 0.1),
      upperLevelSeatingCapacity = 10,
      fieldCapacity = 10,
      boxCapacity = 10,
      fixedPrice = 10.0
    ), "Boca" to Stadium(
      name = "Boca Juniors",
      location = Location(latitud = 0.1, longitud = 0.1),
      upperLevelSeatingCapacity = 10,
      fieldCapacity = 10,
      boxCapacity = 10,
      fixedPrice = 10.0
    )
  )

  fun createFacilities() {
    facilities.values.forEach { facility -> facilityRepository.apply { create(facility) } }
  }

  override fun afterPropertiesSet() {
    println("Facility creation process starts")
    this.createFacilities()
    println("Facility creation process ends")
  }
}