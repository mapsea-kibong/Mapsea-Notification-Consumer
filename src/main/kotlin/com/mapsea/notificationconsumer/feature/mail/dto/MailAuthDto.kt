package com.mapsea.notificationconsumer.feature.mail.dto

import com.fasterxml.jackson.databind.JsonNode

data class MailAuthDto(
    val accessToken: String,
    val expireTimeStamp: Long,
    val scope: String,
    val tokenType: String,
) {
    constructor(jsonNode: JsonNode) : this(
        accessToken = jsonNode.get("access_token").asText(),
        expireTimeStamp = System.currentTimeMillis() * 1000 + 3000000000L,
        scope = jsonNode.get("scope").asText(),
        tokenType = jsonNode.get("token_type").asText(),
    )
}
