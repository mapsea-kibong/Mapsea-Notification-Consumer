package com.mapsea.notificationconsumer.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "service.mail")
data class MailPropertiesConfig(
    val clientId: String = "",
    val clientSecret: String = "",
    val refreshToken: String = "",
    val serviceAuthUrl: String = "",
    val fromMail: String = "",
)
