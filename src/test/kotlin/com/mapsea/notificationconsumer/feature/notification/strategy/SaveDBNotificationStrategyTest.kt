package com.mapsea.notificationconsumer.feature.notification.strategy

import com.mapsea.notificationconsumer.feature.user.repository.UserRepository
import com.mapsea.notificationconsumer.feature.vessel.repository.VesselRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("알림 저장 전략 패턴 테스트")
class SaveDBNotificationStrategyTest {

    @Autowired
    private lateinit var saveDBNotificationStrategy: SaveDBNotificationStrategy

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var vesselRepository: VesselRepository

    @Test
    @DisplayName("알림 저장")
    fun sendNotificationTest() {
        // given
//        val notificationDto = NotificationDto(
//            notificationCategory = NotificationCategory.GEOFENCE,
//            notificationGrade = NotificationGrade.WARNING,
//            notificationSendCategory = NotificationSendCategory.DB,
//            notificationTittle = "209HYUNDAI has entered ECA Test",
//            notificationContent = "209HYUNDAI has entered ECA Test",
//            notificationTimestamp = 1_738_742_632_349_425L,
//            mmsi = "440328880",
//            userId = UUID.fromString("b341c261-3525-11ef-8c22-42010a15b005"),
//        )
//
//        val user = userRepository.findById(UUID.fromString("b341c261-3525-11ef-8c22-42010a15b005")).orElseThrow()
//
//        val vessels = vesselRepository.findAllByMmsiIn(setOf("440328880"))
//        for (vessel in vessels) {
//            saveDBNotificationStrategy.sendNotification(notificationDto, user, vessel, null)
//        }

        // then
    }
}
