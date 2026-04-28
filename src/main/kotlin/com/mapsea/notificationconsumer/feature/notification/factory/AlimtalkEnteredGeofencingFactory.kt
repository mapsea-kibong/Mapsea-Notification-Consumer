package com.mapsea.notificationconsumer.feature.notification.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.notification.dto.AlimtalkRequestDto
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.util.AlimtalkUtil.createAlimtalkButton
import com.mapsea.notificationconsumer.util.TimeUtil.convertTimestamp
import org.springframework.util.LinkedMultiValueMap

class AlimtalkEnteredGeofencingFactory : AlimtalkFactory {

    private val mapper = ObjectMapper()

    override fun createAlimtalk(notificationDto: NotificationDto, user: User, vessel: Vessel): AlimtalkRequestDto {
        val content = buildString {
            append("안녕하세요 ${user.userName}님, 맵시 커넥트입니다.\n\n")
            append("${user.company?.companyName}의 관리 선박 알림을 보내드립니다.\n\n")
            append("- 선박명: ${vessel.shipname}\n")
            append("- 이슈: ${notificationDto.notificationContent?.replace(vessel.shipname ?: "", "")}\n")
            append("- 발생일시: ${convertTimestamp(notificationDto.notificationTimestamp!!)}(UTC)\n\n")
        }

        val requestBody = LinkedMultiValueMap<String, Any>()
        val connectPage = mapper.createObjectNode().apply {
            put("name", "커넥트 바로가기")
            put("linkType", "WL")
            put("linkTypeName", "웹링크")
            put("linkPc", CONNECT_URL)
            put("linkMo", CONNECT_URL)
        }
        createAlimtalkButton(requestBody, listOf(connectPage))

        return AlimtalkRequestDto(
            content = content,
            phoneNumber = user.userMobileNumber ?: "",
            requestBody = requestBody,
            templateCode = TEMPLATE_CODE,
            subject = GEOFENCING_SUBJECT,
        )
    }

    companion object {
        const val CONNECT_URL = "https://connect.mapsea.io"
        const val TEMPLATE_CODE = "TY_4941"
        const val GEOFENCING_SUBJECT = "지오펜싱 알림"
    }
}
