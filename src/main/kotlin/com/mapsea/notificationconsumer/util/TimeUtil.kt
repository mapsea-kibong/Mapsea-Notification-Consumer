package com.mapsea.notificationconsumer.util

import org.slf4j.LoggerFactory
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object TimeUtil {
    private val log = LoggerFactory.getLogger(TimeUtil::class.java)
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"))

    fun convertTimestamp(timestamp: Long): String {
        val instant = Instant.ofEpochMilli(timestamp / 1000)
        val formatted = formatter.format(ZonedDateTime.ofInstant(instant, ZoneId.of("UTC")))
        log.info("convertTimestamp: {}", formatted)
        return formatted
    }
}
