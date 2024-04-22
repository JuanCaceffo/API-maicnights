package ar.edu.unsam.phm.magicnightsback.boostrap

import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Component
@Profile("test")
class TestBootStrap: InitializingBean {

    override fun afterPropertiesSet() {
        // Lógica de inicialización para pruebas
    }
}