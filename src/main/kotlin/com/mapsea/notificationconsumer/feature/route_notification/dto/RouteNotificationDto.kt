package com.mapsea.notificationconsumer.feature.route_notification.dto

import com.fasterxml.jackson.databind.JsonNode
import com.mapsea.notificationconsumer.domain.enums.notification.RouteNotificationCategory
import com.mapsea.notificationconsumer.domain.enums.notification.RouteNotificationGrade
import java.util.UUID

data class RouteNotificationDto(
    val userId: UUID? = null,
    val routePlanId: UUID? = null,
    val companyId: UUID? = null,
    val clientId: UUID? = null,
    val mmsi: String? = null,
    val timestamp: String? = null,
    val routeNotificationCategory: RouteNotificationCategory? = null,
    val routeNotificationGrade: RouteNotificationGrade? = null,
    val routeNotificationTittle: String? = null,
    val routeNotificationContent: String? = null,
    val routeNotificationTimestamp: Long? = null,
) {
    constructor(jsonNode: JsonNode) : this(
        userId = jsonNode.get("userId")?.takeUnless { it.isNull }?.let { UUID.fromString(it.asText()) },
        routePlanId = jsonNode.get("routePlanId")?.takeUnless { it.isNull }?.let { UUID.fromString(it.asText()) },
        companyId = jsonNode.get("companyId")?.takeUnless { it.isNull }?.let { UUID.fromString(it.asText()) },
        clientId = jsonNode.get("clientId")?.takeUnless { it.isNull }?.let { UUID.fromString(it.asText()) },
        mmsi = jsonNode.get("mmsi")?.takeUnless { it.isNull }?.asText(),
        timestamp = jsonNode.get("timestamp")?.takeUnless { it.isNull }?.asText(),
        routeNotificationCategory = jsonNode.get("routeNotificationCategory")
            ?.takeUnless { it.isNull }?.let { RouteNotificationCategory.valueOf(it.asText()) },
        routeNotificationGrade = jsonNode.get("routeNotificationGrade")
            ?.takeUnless { it.isNull }?.let { RouteNotificationGrade.valueOf(it.asText()) },
        routeNotificationTittle = jsonNode.get("routeNotificationTittle")?.takeUnless { it.isNull }?.asText(),
        routeNotificationContent = jsonNode.get("routeNotificationContent")?.takeUnless { it.isNull }?.asText(),
        routeNotificationTimestamp = jsonNode.get("routeNotificationTimestamp")?.takeUnless { it.isNull }?.asLong(),
    )
}
