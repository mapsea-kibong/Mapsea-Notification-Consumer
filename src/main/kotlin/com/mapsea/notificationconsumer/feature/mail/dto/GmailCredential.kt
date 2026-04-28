package com.mapsea.notificationconsumer.feature.mail.dto

data class GmailCredential(
    val client_id: String? = null,
    val client_secret: String? = null,
    val refresh_token: String? = null,
    val grant_type: String? = null,
    val access_token: String? = null,
    val userEmail: String? = null,
)
