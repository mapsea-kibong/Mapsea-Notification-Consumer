package com.mapsea.notificationconsumer.feature.mail.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.mapsea.notificationconsumer.config.MailPropertiesConfig
import com.mapsea.notificationconsumer.feature.mail.dto.GmailCredential
import com.mapsea.notificationconsumer.feature.mail.dto.MailAuthDto
import com.mapsea.notificationconsumer.feature.mail.exception.MailAuthException
import com.mapsea.notificationconsumer.feature.mail.singleton.MailAuthSingleton
import org.springframework.http.HttpEntity
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class MailAuthService(
    private val mailPropertiesConfig: MailPropertiesConfig,
) {
    private val restTemplate = RestTemplate()
    private val objectMapper = ObjectMapper()

    fun getGmailCredential(jsonFactory: JsonFactory): Credential {
        val singleton = MailAuthSingleton.INSTANCE
        var mailAuthDto = singleton.mailAuthDto

        val gmailCredential = GmailCredential(
            client_id = mailPropertiesConfig.clientId,
            client_secret = mailPropertiesConfig.clientSecret,
            refresh_token = mailPropertiesConfig.refreshToken,
            grant_type = "refresh_token",
        )
        try {
            if (mailAuthDto == null || mailAuthDto.expireTimeStamp < System.currentTimeMillis() * 1000) {
                val response = restTemplate.postForObject(
                    mailPropertiesConfig.serviceAuthUrl,
                    HttpEntity(gmailCredential),
                    String::class.java,
                )
                val jsonNode = objectMapper.readTree(response)
                mailAuthDto = MailAuthDto(jsonNode)
                singleton.mailAuthDto = mailAuthDto
            }

            return GoogleCredential.Builder()
                .setTransport(GoogleNetHttpTransport.newTrustedTransport())
                .setJsonFactory(jsonFactory)
                .setClientSecrets(gmailCredential.client_id, gmailCredential.client_secret)
                .build()
                .setAccessToken(mailAuthDto.accessToken)
                .setRefreshToken(gmailCredential.refresh_token)
        } catch (e: Exception) {
            throw MailAuthException(e.message ?: "Mail auth failed")
        }
    }
}
