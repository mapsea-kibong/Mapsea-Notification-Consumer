package com.mapsea.notificationconsumer.feature.route_notification.service

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import com.mapsea.notificationconsumer.feature.mail.service.MailAuthService
import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationEmailDto
import com.mapsea.notificationconsumer.feature.route_notification.dto.SendMailDto
import com.mapsea.notificationconsumer.util.TimeUtil.convertTimestamp
import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.io.ByteArrayOutputStream
import java.util.Base64

@Service
class RouteNotificationMailService(
    private val templateEngine: SpringTemplateEngine,
    private val mailAuthService: MailAuthService,
    private val javaMailSender: JavaMailSender,
) {
    fun sendMonitoringMail(dto: RouteNotificationEmailDto) {
        val mailDto = convertMailInfo(dto)
        sendMail(mailDto)
    }

    fun createMailTemplate(dto: RouteNotificationEmailDto): String {
        val context = Context().apply {
            setVariable("companyName", dto.companyName)
            setVariable("shipName", dto.vesselName)
            setVariable("message", dto.eventContent)
            setVariable("time", convertTimestamp(dto.routeNotificationTimestamp) + " (UTC)")
            setVariable(
                "connectUrl",
                if (dto.vesselName.contains("KEOYOUNG")) "https://keoyoung.connect.mapsea.io/kr"
                else "https://connect.mapsea.io/kr",
            )
        }
        return templateEngine.process("routeNotificationMail", context)
    }

    fun convertMailInfo(dto: RouteNotificationEmailDto): SendMailDto {
        val subject = "[mapsea CONNECT] ${dto.vesselName} Route monitoring 알림입니다."
        return SendMailDto(toEmail = dto.email, subject = subject, text = createMailTemplate(dto))
    }

    fun sendMail(mailDto: SendMailDto) {
        val jsonFactory = GsonFactory.getDefaultInstance()
        val credential = mailAuthService.getGmailCredential(jsonFactory)
        val gmailConfig = Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
            .setApplicationName("mapsea-notification-consumer")
            .build()
        val message = createMessageWithEmail(createEmail(mailDto))
        gmailConfig.users().messages().send("no-reply@mapseacorp.com", message).execute()
    }

    fun createEmail(mailDto: SendMailDto): MimeMessage {
        val mimeMessage = javaMailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, false, "UTF-8").apply {
            setTo(mailDto.toEmail)
            setFrom("no-reply@mapseacorp.com")
            setSubject(mailDto.subject)
            setText(mailDto.text, true)
        }
        return mimeMessage
    }

    fun createMessageWithEmail(email: MimeMessage): Message {
        val buffer = ByteArrayOutputStream()
        email.writeTo(buffer)
        return Message().apply { raw = Base64.getUrlEncoder().encodeToString(buffer.toByteArray()) }
    }
}
