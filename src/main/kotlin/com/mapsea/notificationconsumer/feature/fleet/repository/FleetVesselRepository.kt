package com.mapsea.notificationconsumer.feature.fleet.repository

import com.mapsea.notificationconsumer.domain.FleetVessel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FleetVesselRepository : JpaRepository<FleetVessel, UUID>
