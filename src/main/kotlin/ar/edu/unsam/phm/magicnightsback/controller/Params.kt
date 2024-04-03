package ar.edu.unsam.phm.magicnightsback.controller

import org.springframework.web.bind.annotation.RequestParam

open class BaseFilterParams(
    @RequestParam(name = "keyword", required = false)  val keyword: String = "",
    @RequestParam(name = "withFriends", required = false) val withFriends: Boolean = false
)
