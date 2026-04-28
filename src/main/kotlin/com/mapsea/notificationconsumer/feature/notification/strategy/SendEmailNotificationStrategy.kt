package com.mapsea.notificationconsumer.feature.notification.strategy

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.gmail.Gmail
import com.google.api.services.gmail.model.Message
import com.mapsea.notificationconsumer.domain.RoutePlan
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategory
import com.mapsea.notificationconsumer.feature.company.repository.CompanyQueryRepository
import com.mapsea.notificationconsumer.feature.mail.service.MailAuthService
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationEmailDto
import com.mapsea.notificationconsumer.feature.route_notification.dto.SendMailDto
import com.mapsea.notificationconsumer.feature.route_notification.service.RouteNotificationMailService
import jakarta.mail.internet.MimeMessage
import jakarta.transaction.Transactional
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import java.io.ByteArrayOutputStream
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Base64

@Component
class SendEmailNotificationStrategy(
    private val mailAuthService: MailAuthService,
    private val javaMailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine,
    private val companyQueryRepository: CompanyQueryRepository,
    private val routeNotificationMailService: RouteNotificationMailService,
) : SendNotificationStrategy {

    private val log = LoggerFactory.getLogger(SendEmailNotificationStrategy::class.java)

    @Transactional
    override fun sendNotification(notificationDto: NotificationDto, user: User, vessel: Vessel, routePlan: RoutePlan?) {
        try {
            if (notificationDto.notificationCategory == NotificationCategory.ROUTE_PLAN) {
                val company = companyQueryRepository.findCompanyByUser(user.userId)
                routeNotificationMailService.sendMonitoringMail(
                    RouteNotificationEmailDto(
                        email = user.userEmail ?: "",
                        eventContent = notificationDto.notificationTittle ?: "",
                        vesselName = vessel.shipname ?: "",
                        companyName = company?.companyName ?: "",
                        routeNotificationTimestamp = notificationDto.notificationTimestamp ?: 0L,
                    ),
                )
            } else {
                val jsonFactory: JsonFactory = GsonFactory.getDefaultInstance()
                val gmailConfig = createGmailConfig(jsonFactory, mailAuthService.getGmailCredential(jsonFactory))
                val mailTemplate = createMailTemplate(notificationDto, user, vessel)
                val mailDto = SendMailDto(
                    toEmail = user.userEmail ?: "",
                    subject = notificationDto.notificationTittle ?: "",
                    text = mailTemplate,
                )
                val message = createMessageWithEmail(createEmail(mailDto))
                val result = gmailConfig.users().messages().send("no-reply@mapseacorp.com", message).execute()
                log.info("Message Id: {} , email: {}", result.id, user.userEmail)
            }
        } catch (e: Exception) {
            log.error("Error while sending notification: {}", e.message)
        }
    }

    fun createMailTemplate(notificationDto: NotificationDto, user: User, vessel: Vessel): String {
        val company = companyQueryRepository.findCompanyByUser(user.userId)
        val context = Context().apply {
            setVariable("companyName", company?.companyName)
            setVariable("shipName", vessel.shipname)
            setVariable("message", notificationDto.notificationContent)
            val instant = Instant.ofEpochMilli((notificationDto.notificationTimestamp ?: 0L) / 1000)
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("UTC"))
            setVariable("time", formatter.format(ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"))) + " (UTC)")
            setVariable(
                "connectUrl",
                if (vessel.shipname?.contains("KEOYOUNG") == true) "https://keoyoung.connect.mapsea.io/kr"
                else "https://connect.mapsea.io/kr",
            )
        }
        return templateEngine.process("geofencingNotification.html", context)
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

    private fun createGmailConfig(jsonFactory: JsonFactory, credential: Credential): Gmail =
        Gmail.Builder(GoogleNetHttpTransport.newTrustedTransport(), jsonFactory, credential)
            .setApplicationName("mapsea-notification-consumer")
            .build()
}
