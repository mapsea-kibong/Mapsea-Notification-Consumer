package com.mapsea.notificationconsumer.feature.route_notification.dto

data class SendMailDto(
    val toEmail: String,
    val subject: String,
    val text: String,
)
