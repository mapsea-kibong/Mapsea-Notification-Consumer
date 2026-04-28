package com.mapsea.notificationconsumer.config.properties

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class ApiProperties {

    @Value("\${api.key.aligo}")
    lateinit var aligoKey: String

    @Value("\${api.key.aligo-sender}")
    lateinit var aligoSenderKey: String

    @Value("\${api.base-url.aligo}")
    lateinit var aligoBaseUrl: String
}
