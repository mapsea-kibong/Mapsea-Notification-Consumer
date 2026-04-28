package com.mapsea.notificationconsumer.feature.notification.service

import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategory
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategoryDetail
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationGrade
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationSendCategory
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    private lateinit var notificationService: NotificationService

    @Test
    fun sendAndSaveNotification() {
        val dto = NotificationDto(
            notificationCategory = NotificationCategory.GEOFENCE,
            notificationGrade = NotificationGrade.WARNING,
            notificationSendCategory = NotificationSendCategory.KAKAO,
            notificationCategoryDetail = NotificationCategoryDetail.ECA,
            notificationTittle = "MT MATRIX PRIDE entered JWC-Gulf of Guinea",
            notificationContent = "entered JWC-Gulf of Guinea",
            notificationTimestamp = 1741592479000000L,
            mmsi = "657146700",
            userId = UUID.fromString("A49E54CE-3D9E-11EF-8C22-42010A15B005"),
            routePlanId = null,
        )

        notificationService.sendAndSaveNotification(listOf(dto))
    }
}
