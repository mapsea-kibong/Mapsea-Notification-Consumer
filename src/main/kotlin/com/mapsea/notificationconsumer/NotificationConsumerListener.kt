package com.mapsea.notificationconsumer

import com.fasterxml.jackson.databind.ObjectMapper
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.feature.notification.service.NotificationService
import com.mapsea.notificationconsumer.singleton.NotificationTimestampSingleton
import org.apache.kafka.clients.consumer.ConsumerRecords
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class NotificationConsumerListener(
    private val objectMapper: ObjectMapper,
    private val notificationService: NotificationService,
) {
    private val log = LoggerFactory.getLogger(NotificationConsumerListener::class.java)

    @KafkaListener(
        topics = ["#{consumerTopicNotification}"],
        groupId = "#{consumerGroupId}",
        concurrency = "3",
    )
    fun sendNotification(consumerRecords: ConsumerRecords<String, String>) {
        val timestampSingleton = NotificationTimestampSingleton.INSTANCE
        val notificationTimestampMap = timestampSingleton.notificationTimestampMap
        val removeList = mutableListOf<NotificationDto>()

        try {
            val notificationDtoList = consumerRecords.map { record ->
                objectMapper.readValue(record.value(), NotificationDto::class.java)
            }.toMutableList()

            for (dto in notificationDtoList) {
                log.info(dto.toString())
                val key = "${dto.userId}:${dto.mmsi}:${dto.notificationSendCategory}:${dto.notificationTittle}"
                val existingTimestamp = notificationTimestampMap[key]
                if (existingTimestamp != null) {
                    if (existingTimestamp < (dto.notificationTimestamp ?: 0L)) {
                        notificationTimestampMap[key] = dto.notificationTimestamp ?: 0L
                    } else {
                        removeList.add(dto)
                    }
                } else {
                    notificationTimestampMap[key] = dto.notificationTimestamp ?: 0L
                }
            }

            notificationDtoList.removeAll(removeList)
            notificationService.sendAndSaveNotification(notificationDtoList)
        } catch (e: Exception) {
            log.error("sendNotification error", e)
        }
    }
}
