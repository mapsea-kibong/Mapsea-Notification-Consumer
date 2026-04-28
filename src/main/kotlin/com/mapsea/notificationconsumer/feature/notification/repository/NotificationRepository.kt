package com.mapsea.notificationconsumer.feature.notification.repository

import com.mapsea.notificationconsumer.domain.Notification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface NotificationRepository : JpaRepository<Notification, UUID>
