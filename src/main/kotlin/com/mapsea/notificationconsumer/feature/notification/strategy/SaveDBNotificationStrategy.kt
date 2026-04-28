package com.mapsea.notificationconsumer.feature.notification.strategy

import com.mapsea.notificationconsumer.domain.Notification
import com.mapsea.notificationconsumer.domain.RoutePlan
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.feature.notification.repository.NotificationRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component

@Component
class SaveDBNotificationStrategy(
    private val notificationRepository: NotificationRepository,
) : SendNotificationStrategy {

    @Transactional
    override fun sendNotification(notificationDto: NotificationDto, user: User, vessel: Vessel, routePlan: RoutePlan?) {
        notificationRepository.save(Notification.createNotificationByDto(notificationDto, user, vessel, routePlan))
    }
}
