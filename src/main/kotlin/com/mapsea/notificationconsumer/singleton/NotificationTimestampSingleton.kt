package com.mapsea.notificationconsumer.singleton

import java.util.concurrent.ConcurrentHashMap

enum class NotificationTimestampSingleton {
    INSTANCE;

    val notificationTimestampMap: MutableMap<String, Long> = ConcurrentHashMap()
}
