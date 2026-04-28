package com.mapsea.notificationconsumer.feature.route_notification.dto

data class RouteNotificationEmailDto(
    val email: String,
    val eventContent: String,
    val vesselName: String,
    val companyName: String,
    val routeNotificationTimestamp: Long,
)
