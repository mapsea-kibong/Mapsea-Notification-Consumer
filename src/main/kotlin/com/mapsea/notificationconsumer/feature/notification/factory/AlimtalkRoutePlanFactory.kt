package com.mapsea.notificationconsumer.feature.notification.factory

import com.fasterxml.jackson.databind.ObjectMapper
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.company.repository.CompanyQueryRepository
import com.mapsea.notificationconsumer.feature.notification.dto.AlimtalkRequestDto
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.util.AlimtalkUtil.createAlimtalkButton
import com.mapsea.notificationconsumer.util.TimeUtil.convertTimestamp
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap

@Component
class AlimtalkRoutePlanFactory(
    val companyQueryRepository: CompanyQueryRepository,
) : AlimtalkFactory {

    private val log = LoggerFactory.getLogger(AlimtalkRoutePlanFactory::class.java)
    private val mapper = ObjectMapper()

    override fun createAlimtalk(notificationDto: NotificationDto, user: User, vessel: Vessel): AlimtalkRequestDto? {
        return try {
            val company = companyQueryRepository.findCompanyByUser(user.userId)

            val content = buildString {
                append("안녕하세요 ${user.userName}님, 맵시 커넥트입니다.\n\n")
                append("${company?.companyName}의 관리 선박 알림을 보내드립니다.\n\n")
                append("- 선박명: ${vessel.shipname}\n")
                append("- 이슈: ${notificationDto.notificationContent}\n")
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

            AlimtalkRequestDto(
                content = content,
                phoneNumber = user.userMobileNumber ?: "",
                requestBody = requestBody,
                templateCode = TEMPLATE_CODE,
                subject = ROUTE_PLAN_SUBJECT,
            )
        } catch (e: ArrayIndexOutOfBoundsException) {
            log.error("notificationTitle={}", notificationDto.notificationTittle)
            null
        }
    }

    companion object {
        const val CONNECT_URL = "https://connect.mapsea.io/"
        const val TEMPLATE_CODE = "TX_8786"
        const val ROUTE_PLAN_SUBJECT = "루트 모니터링"
    }
}
