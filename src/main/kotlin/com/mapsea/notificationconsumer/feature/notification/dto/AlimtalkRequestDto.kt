package com.mapsea.notificationconsumer.feature.notification.dto

import org.springframework.util.MultiValueMap

data class AlimtalkRequestDto(
    val content: String,
    val phoneNumber: String,
    val requestBody: MultiValueMap<String, Any>,
    val templateCode: String,
    val subject: String,
)
