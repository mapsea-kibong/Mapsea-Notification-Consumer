package com.mapsea.notificationconsumer.feature.notification.strategy

import com.mapsea.notificationconsumer.domain.RoutePlan
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto

interface SendNotificationStrategy {
    fun sendNotification(notificationDto: NotificationDto, user: User, vessel: Vessel, routePlan: RoutePlan?)
}
