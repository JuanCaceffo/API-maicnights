package ar.edu.unsam.phm.magicnightsback.jsonsViews

import com.fasterxml.jackson.annotation.JsonView

class LoginUser(
    @JsonView(Views.Public::class)
    var username: String = "",

    @JsonView(Views.Public::class)
    var password: String = "",
){ }