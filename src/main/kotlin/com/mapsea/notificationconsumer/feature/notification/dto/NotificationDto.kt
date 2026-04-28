package com.mapsea.notificationconsumer.feature.notification.dto

import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategory
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategoryDetail
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationGrade
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationSendCategory
import java.util.UUID

data class NotificationDto(
    val notificationCategory: NotificationCategory? = null,
    val notificationGrade: NotificationGrade? = null,
    val notificationSendCategory: NotificationSendCategory? = null,
    val notificationCategoryDetail: NotificationCategoryDetail? = null,
    val notificationTittle: String? = null,
    val notificationContent: String? = null,
    val notificationTimestamp: Long? = null,
    val mmsi: String? = null,
    val userId: UUID? = null,
    val routePlanId: UUID? = null,
)
