package com.mapsea.notificationconsumer.feature.alim_talk

import com.mapsea.notificationconsumer.config.properties.ApiProperties
import com.mapsea.notificationconsumer.feature.notification.dto.AlimtalkRequestDto
import org.slf4j.LoggerFactory
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

@Service
class AlimTalkService(
    private val apiProperties: ApiProperties,
) {
    private val log = LoggerFactory.getLogger(AlimTalkService::class.java)
    private val restTemplate = RestTemplate()

    fun sendMessage(alimtalkRequestDto: AlimtalkRequestDto?) {
        alimtalkRequestDto ?: return
        val requestBody = alimtalkRequestDto.requestBody
        requestBody.add("apikey", apiProperties.aligoKey)
        requestBody.add("userid", USER_ID)
        requestBody.add("senderkey", apiProperties.aligoSenderKey)
        requestBody.add("sender", SENDER_PHONE_NUMBER)
        requestBody.add("receiver_1", alimtalkRequestDto.phoneNumber)
        requestBody.add("tpl_code", alimtalkRequestDto.templateCode)
        requestBody.add("subject_1", alimtalkRequestDto.subject)
        requestBody.add("message_1", alimtalkRequestDto.content)

        val headers = HttpHeaders().apply { contentType = MediaType.APPLICATION_FORM_URLENCODED }
        val response = restTemplate.exchange(
            "https://kakaoapi.aligo.in/akv10/alimtalk/send/",
            HttpMethod.POST,
            HttpEntity(requestBody, headers),
            String::class.java,
        )
        log.info("Alimtalk code: {}", response.statusCode)
        log.info("Alimtalk response: {}", response.body)
    }

    companion object {
        private const val USER_ID = "mapsea"
        private const val SENDER_PHONE_NUMBER = "07044690213"
    }
}
