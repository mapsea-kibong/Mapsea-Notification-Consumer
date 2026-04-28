package com.mapsea.notificationconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class MapseaNotificationConsumerApplication

fun main(args: Array<String>) {
    runApplication<MapseaNotificationConsumerApplication>(*args)
}
