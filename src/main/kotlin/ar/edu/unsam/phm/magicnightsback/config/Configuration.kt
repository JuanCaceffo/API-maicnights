package ar.edu.unsam.phm.magicnightsback.config

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(ApiProperties::class)
class Configuration {
}