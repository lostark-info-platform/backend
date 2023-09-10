package org.info.lostark

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class LostarkApplication

fun main(args: Array<String>) {
    runApplication<LostarkApplication>(*args)
}
