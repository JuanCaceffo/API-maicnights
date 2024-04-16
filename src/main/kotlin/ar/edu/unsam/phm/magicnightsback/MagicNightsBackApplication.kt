package ar.edu.unsam.phm.magicnightsback

import org.springdoc.core.models.GroupedOpenApi
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class MagicNightsBackApplication

fun main(args: Array<String>) {
    runApplication<MagicNightsBackApplication>(*args)
}

@Configuration
class SwaggerConfig {
    @Bean
    fun springShopOpenApi(): GroupedOpenApi {
        val paths = arrayOf("/api/**")
        return GroupedOpenApi.builder()
            .group("springdoc")
            .pathsToMatch(*paths)
            .build()
    }
}