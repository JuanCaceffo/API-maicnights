package ar.edu.unsam.phm.magicnightsback.controller

import org.springframework.web.bind.annotation.RequestParam

open class BaseFilterParams(
    @RequestParam(required = false, defaultValue = "-1") val userId: Long,
    @RequestParam(name = "bandKeyword", required = false)  val bandKeyword: String = "",
    @RequestParam(name = "facilityKeyword", required = false)  val facilityKeyword: String = "",
    @RequestParam(name = "withFriends", required = false) val withFriends: Boolean = false
)
