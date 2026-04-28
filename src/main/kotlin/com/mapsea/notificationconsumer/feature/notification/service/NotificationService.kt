package com.mapsea.notificationconsumer.feature.notification.service

import com.mapsea.notificationconsumer.domain.enums.notification.NotificationSendCategory
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.feature.notification.strategy.SaveDBNotificationStrategy
import com.mapsea.notificationconsumer.feature.notification.strategy.SendAlimTalkNotificationStrategy
import com.mapsea.notificationconsumer.feature.notification.strategy.SendEmailNotificationStrategy
import com.mapsea.notificationconsumer.feature.notification.strategy.SendNotificationStrategy
import com.mapsea.notificationconsumer.feature.route_plan.repository.RoutePlanQueryRepository
import com.mapsea.notificationconsumer.feature.user.repository.UserQueryRepository
import com.mapsea.notificationconsumer.feature.vessel.repository.VesselRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.NoSuchElementException
import java.util.UUID

@Service
class NotificationService(
    private val vesselRepository: VesselRepository,
    private val userQueryRepository: UserQueryRepository,
    private val routePlanQueryRepository: RoutePlanQueryRepository,
    private val saveDBNotificationStrategy: SaveDBNotificationStrategy,
    private val sendEmailNotificationStrategy: SendEmailNotificationStrategy,
    private val alimTalkNotificationStrategy: SendAlimTalkNotificationStrategy,
) {
    private val log = LoggerFactory.getLogger(NotificationService::class.java)

    fun sendAndSaveNotification(notificationDtoList: List<NotificationDto>) {
        val userIdList = notificationDtoList.mapNotNull { it.userId }
        val userList = userQueryRepository.findUserList(userIdList)

        val vesselSet = vesselRepository.findAllByMmsiIn(
            notificationDtoList.mapNotNull { it.mmsi }.toSet(),
        )
        val routePlanIdSet = notificationDtoList.mapNotNull { it.routePlanId }.toMutableSet()
        val routePlans = if (routePlanIdSet.isNotEmpty()) {
            routePlanQueryRepository.routePlanListInUUID(routePlanIdSet)
        } else emptyList()

        for (dto in notificationDtoList) {
            try {
                val userId: UUID = dto.userId ?: throw NoSuchElementException("userId is null")
                val user = userList.first { it.userId == userId }
                val vessel = vesselSet.first { it.mmsi == dto.mmsi }

                val strategy: SendNotificationStrategy = when (dto.notificationSendCategory) {
                    NotificationSendCategory.EMAIL -> sendEmailNotificationStrategy
                    NotificationSendCategory.KAKAO -> alimTalkNotificationStrategy
                    else -> saveDBNotificationStrategy
                }

                val routePlan = dto.routePlanId?.let { id -> routePlans.firstOrNull { it.id == id } }
                strategy.sendNotification(dto, user, vessel, routePlan)
            } catch (e: NoSuchElementException) {
                log.error("Error while sending notification: {}", e.message)
            }
        }
    }
}
