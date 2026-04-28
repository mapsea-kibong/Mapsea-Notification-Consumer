package com.mapsea.notificationconsumer.alim_talk

import com.mapsea.notificationconsumer.feature.alim_talk.AlimTalkService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AlimTalkTest {

    @Autowired
    private lateinit var alimTalkService: AlimTalkService

    @Test
    fun contextLoads() {
        // alimTalkService.sendMessage(RouteNotificationAlimtalkDto("010-6762-0766", "KEOYOUNG BLUE 3", "채지혜", "Out Of Xtd"))
    }
}
