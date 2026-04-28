package com.mapsea.notificationconsumer.feature.route_notification.repository

import com.mapsea.notificationconsumer.domain.RouteNotification
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RouteNotificationRepository : JpaRepository<RouteNotification, UUID>
