package com.mapsea.notificationconsumer.feature.route_notification.service

import com.mapsea.notificationconsumer.domain.RouteNotification
import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationAlimtalkDto
import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationDto
import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationEmailDto
import com.mapsea.notificationconsumer.feature.route_notification.repository.RouteNotificationRepository
import com.mapsea.notificationconsumer.feature.route_plan.repository.RoutePlanQueryRepository
import com.mapsea.notificationconsumer.feature.user.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RouteNotificationService(
    private val routeNotificationRepository: RouteNotificationRepository,
    private val userRepository: UserRepository,
    private val routePlanQueryRepository: RoutePlanQueryRepository,
    private val routeNotificationMailService: RouteNotificationMailService,
) {
    private val log = LoggerFactory.getLogger(RouteNotificationService::class.java)

    fun saveBulkRouteNotification(dtoList: List<RouteNotificationDto>) {
        val routePlanIdSet = dtoList.mapNotNull { it.routePlanId }.toSet()
        val userIdSet = dtoList.mapNotNull { it.userId }.toSet()
        val userList = userRepository.findAllById(userIdSet)
        val routePlanList = routePlanQueryRepository.routePlanListInUUID(routePlanIdSet)

        val userMap = userList.associateBy { it.userId }
        val routePlanMap = routePlanList.associateBy { it.id }

        val notifications = dtoList.map { dto ->
            RouteNotification(dto).apply {
                updateUserRoutePlan(
                    userMap[dto.userId],
                    routePlanMap[dto.routePlanId],
                )
            }
        }

        notifications.forEach { notification ->
            val user = notification.user ?: return@forEach
            val shipname = notification.routePlan?.voyage?.companyVessel?.vessel?.shipname ?: ""

            if (user.userEmail != null) {
                try {
                    routeNotificationMailService.sendMonitoringMail(
                        RouteNotificationEmailDto(
                            email = user.userEmail!!,
                            eventContent = notification.routeNotificationTittle ?: "",
                            vesselName = shipname,
                            companyName = user.company?.companyName ?: "",
                            routeNotificationTimestamp = notification.routeNotificationTimestamp ?: 0L,
                        ),
                    )
                } catch (e: Exception) {
                    log.error("RouteNotificationService saveBulkRouteNotification error: {}", e.message)
                }
            }
            if (user.userMobileNumber != null) {
                RouteNotificationAlimtalkDto(
                    userMobileNo = user.userMobileNumber!!,
                    vesselName = shipname,
                    userName = user.userName ?: "",
                    content = notification.routeNotificationTittle ?: "",
                )
            }
        }

        routeNotificationRepository.saveAll(notifications)
    }
}
