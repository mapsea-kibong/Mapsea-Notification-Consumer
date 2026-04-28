package com.mapsea.notificationconsumer.feature.notification.factory

import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.notification.dto.AlimtalkRequestDto
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto

interface AlimtalkFactory {
    fun createAlimtalk(notificationDto: NotificationDto, user: User, vessel: Vessel): AlimtalkRequestDto?
}
