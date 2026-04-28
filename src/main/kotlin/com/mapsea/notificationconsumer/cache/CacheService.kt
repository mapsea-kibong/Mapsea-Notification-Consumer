package com.mapsea.notificationconsumer.cache

import com.mapsea.notificationconsumer.GeoPointDto
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.vessel.repository.VesselRepository
import org.springframework.cache.annotation.CachePut
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheService(
    private val vesselRepository: VesselRepository,
) {
    @Cacheable(value = ["vesselCache"], key = "#vesselMmsi", unless = "#result == null")
    fun getVesselCache(vesselMmsi: String): Vessel? =
        vesselRepository.findVesselByMmsi(vesselMmsi)

    @CachePut(value = ["vesselGeoCache"], key = "#vesselMmsi")
    fun updateVesselGeoCache(vesselMmsi: String, geoPointDto: GeoPointDto): GeoPointDto = geoPointDto

    @Cacheable(value = ["vesselGeoCache"], key = "#vesselMmsi")
    fun getVesselGeoCache(vesselMmsi: String): GeoPointDto? = null
}
