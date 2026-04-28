package com.mapsea.notificationconsumer.feature.notification.strategy

import com.mapsea.notificationconsumer.feature.user.repository.UserRepository
import com.mapsea.notificationconsumer.feature.vessel.repository.VesselRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
@DisplayName("알림 이메일 전송 전략 패턴 테스트")
class SaveEmailNotificationStrategyTest {

    @Autowired
    private lateinit var sendEmailNotificationStrategy: SendEmailNotificationStrategy

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var vesselRepository: VesselRepository

    @Test
    @DisplayName("알림 전송 테스트")
    fun sendNotificationTest() {
        // given
//        val notificationDto = NotificationDto(
//            notificationCategory = NotificationCategory.ROUTE_PLAN,
//            notificationGrade = NotificationGrade.WARNING,
//            notificationSendCategory = NotificationSendCategory.EMAIL,
//            notificationTittle = "209HYUNDAI has entered ECA",
//            notificationContent = "209HYUNDAI has entered ECA",
//            notificationTimestamp = 1_738_742_632_349_425L,
//            mmsi = "440328880",
//            userId = UUID.fromString("b341c261-3525-11ef-8c22-42010a15b005"),
//        )
//
//        val user = userRepository.findById(UUID.fromString("b341c261-3525-11ef-8c22-42010a15b005")).orElseThrow()
//
//        val vessels = vesselRepository.findAllByMmsiIn(setOf("440328880"))
//        for (vessel in vessels) {
//            sendEmailNotificationStrategy.sendNotification(notificationDto, user, vessel, null)
//        }

        // then
    }
}
