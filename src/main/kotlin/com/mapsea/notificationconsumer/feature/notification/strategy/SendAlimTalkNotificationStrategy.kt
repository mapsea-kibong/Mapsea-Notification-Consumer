package com.mapsea.notificationconsumer.feature.notification.strategy

import com.mapsea.notificationconsumer.domain.RoutePlan
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategory
import com.mapsea.notificationconsumer.feature.alim_talk.AlimTalkService
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.feature.notification.factory.AlimtalkEnteredGeofencingFactory
import com.mapsea.notificationconsumer.feature.notification.factory.AlimtalkFactory
import com.mapsea.notificationconsumer.feature.notification.factory.AlimtalkRoutePlanFactory
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class SendAlimTalkNotificationStrategy(
    private val alimTalkService: AlimTalkService,
    private val alimtalkRoutePlanFactory: AlimtalkRoutePlanFactory,
) : SendNotificationStrategy {

    private val log = LoggerFactory.getLogger(SendAlimTalkNotificationStrategy::class.java)

    @Transactional
    override fun sendNotification(notificationDto: NotificationDto, user: User, vessel: Vessel, routePlan: RoutePlan?) {
        val mobileNumber = user.userMobileNumber
        if (mobileNumber.isNullOrBlank()) return

        when (notificationDto.notificationCategory) {
            NotificationCategory.GEOFENCE ->
                alimTalkService.sendMessage(
                    createAlimtalkRequest(notificationDto, user, vessel, AlimtalkEnteredGeofencingFactory()),
                )
            NotificationCategory.ROUTE_PLAN ->
                alimTalkService.sendMessage(
                    createAlimtalkRequest(notificationDto, user, vessel, alimtalkRoutePlanFactory),
                )
            else -> log.error("Notification category is not supported")
        }
    }

    private fun createAlimtalkRequest(
        notificationDto: NotificationDto,
        user: User,
        vessel: Vessel,
        factory: AlimtalkFactory,
    ) = factory.createAlimtalk(notificationDto, user, vessel)
}
