package com.mapsea.notificationconsumer.feature.vessel.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.mapsea.notificationconsumer.domain.Vessel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VesselRepository : JpaRepository<Vessel, UUID>, KotlinJdslJpqlExecutor {
    fun findVesselByMmsi(mmsi: String): Vessel?
    fun findAllByMmsiIn(mmsiList: Set<String>): Set<Vessel>
}
