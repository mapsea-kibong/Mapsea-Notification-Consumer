package com.mapsea.notificationconsumer

enum class CacheType(
    val cacheName: String,
    val expiredAfterWrite: Int,
    val maximumSize: Int,
) {
    VESSEL("vesselCache", 12, 10000),
    VESSEL_GEOPOINT("vesselGeoCache", 12, 10000),
}
