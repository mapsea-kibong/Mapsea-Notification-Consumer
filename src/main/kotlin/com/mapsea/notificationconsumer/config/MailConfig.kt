package com.mapsea.notificationconsumer.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.JavaMailSenderImpl

@Configuration
class MailConfig {

    @Bean
    fun javaMailService(): JavaMailSender = JavaMailSenderImpl()
}
