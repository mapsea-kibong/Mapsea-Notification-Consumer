package com.mapsea.notificationconsumer.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaGroupIdConfig {

    @Value("\${spring.kafka.consumer.group-id}")
    private lateinit var consumerGroupId: String

    @Value("\${spring.kafka.consumer.topic.notification}")
    private lateinit var consumerTopicNotification: String

    @Bean
    fun consumerGroupId(): String = consumerGroupId

    @Bean
    fun consumerTopicNotification(): String = consumerTopicNotification
}
