package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import com.mapsea.notificationconsumer.domain.enums.notification.RouteNotificationCategory
import com.mapsea.notificationconsumer.domain.enums.notification.RouteNotificationGrade
import com.mapsea.notificationconsumer.feature.route_notification.dto.RouteNotificationDto
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
@Table(name = "route_notification", catalog = "fms_dev")
class RouteNotification(
    @Id
    @Column(name = "route_notification_id")
    @Comment("알림 아이디")
    var routeNotificationId: UUID = Generators.timeBasedGenerator().generate(),

    @Column(name = "route_notification_category")
    @Enumerated(EnumType.STRING)
    @Comment("알림 종류")
    var routeNotificationCategory: RouteNotificationCategory? = null,

    @Column(name = "route_notification_grade")
    @Enumerated(EnumType.STRING)
    @Comment("알림 등급")
    var routeNotificationGrade: RouteNotificationGrade? = null,

    @Column(name = "route_notification_tittle")
    @Comment("알림 제목")
    var routeNotificationTittle: String? = null,

    @Column(name = "route_notification_content")
    @Comment("알림 내용")
    var routeNotificationContent: String? = null,

    @Column(name = "route_notification_timestamp")
    @Comment("알림 시간")
    var routeNotificationTimestamp: Long? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var user: User? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id", referencedColumnName = "id")
    var routePlan: RoutePlan? = null,
) : Auditable() {

    constructor(routeNotificationDto: RouteNotificationDto) : this(
        routeNotificationId = Generators.timeBasedGenerator().generate(),
        routeNotificationCategory = routeNotificationDto.routeNotificationCategory,
        routeNotificationGrade = routeNotificationDto.routeNotificationGrade,
        routeNotificationTittle = routeNotificationDto.routeNotificationTittle,
        routeNotificationContent = routeNotificationDto.routeNotificationContent,
        routeNotificationTimestamp = routeNotificationDto.routeNotificationTimestamp,
        user = null,
        routePlan = null,
    )

    fun updateUserRoutePlan(user: User?, routePlan: RoutePlan?) {
        this.user = user
        this.routePlan = routePlan
    }
}
