package com.mapsea.notificationconsumer.mail

import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationEmailDto
import com.mapsea.notificationconsumer.feature.route_notification.service.RouteNotificationMailService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MailTest {

    @Autowired
    private lateinit var routeNotificationMailService: RouteNotificationMailService

    @Test
    fun test() {
        val dto = RouteNotificationEmailDto("jh.chae@mapseacorp.com", "Out Of Xtd", "KiBong", "Mapsea", 1727246321000000L)
        routeNotificationMailService.sendMonitoringMail(dto)
    }
}
