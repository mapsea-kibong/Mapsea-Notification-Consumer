package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategory
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationCategoryDetail
import com.mapsea.notificationconsumer.domain.enums.notification.NotificationGrade
import com.mapsea.notificationconsumer.feature.notification.dto.NotificationDto
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.util.UUID

@Entity
@Table(name = "notification")
class Notification(
    @Id
    @Column(name = "notification_id")
    @Comment("알림 아이디")
    var notificationId: UUID = Generators.timeBasedGenerator().generate(),

    @Column(name = "notification_category")
    @Enumerated(EnumType.STRING)
    @Comment("알림 종류")
    var notificationCategory: NotificationCategory? = null,

    @Column(name = "notification_grade")
    @Enumerated(EnumType.STRING)
    @Comment("알림 등급")
    var notificationGrade: NotificationGrade? = null,

    @Column(name = "notification_tittle")
    @Comment("알림 제목")
    var notificationTittle: String? = null,

    @Column(name = "notification_category_detail", nullable = true)
    @Enumerated(EnumType.STRING)
    @Comment("알림 종류 상세")
    var notificationCategoryDetail: NotificationCategoryDetail? = null,

    @Column(name = "notification_content")
    @Comment("알림 내용")
    var notificationContent: String? = null,

    @Column(name = "notification_timestamp")
    @Comment("알림 시간")
    var notificationTimestamp: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id")
    var vessel: Vessel? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_plan_id", nullable = true)
    var routePlan: RoutePlan? = null,
) : Auditable() {

    companion object {
        fun createNotificationByDto(
            notificationDto: NotificationDto,
            user: User,
            vessel: Vessel,
            routePlan: RoutePlan?,
        ): Notification = Notification(
            notificationId = Generators.timeBasedGenerator().generate(),
            notificationCategory = notificationDto.notificationCategory,
            notificationGrade = notificationDto.notificationGrade,
            notificationTittle = notificationDto.notificationTittle,
            notificationContent = notificationDto.notificationContent,
            notificationTimestamp = notificationDto.notificationTimestamp,
            notificationCategoryDetail = notificationDto.notificationCategoryDetail,
            user = user,
            vessel = vessel,
            routePlan = routePlan,
        )
    }
}
