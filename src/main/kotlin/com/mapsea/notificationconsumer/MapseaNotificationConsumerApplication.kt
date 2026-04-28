package com.mapsea.notificationconsumer

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableAsync

@SpringBootApplication
@EnableAsync
class MapseaNotificationConsumerApplication

fun main(args: Array<String>) {
    runApplication<MapseaNotificationConsumerApplication>(*args)
}
