package com.mapsea.notificationconsumer.feature.mail.singleton

import com.mapsea.notificationconsumer.feature.mail.dto.MailAuthDto

enum class MailAuthSingleton {
    INSTANCE;

    var mailAuthDto: MailAuthDto? = null
}
