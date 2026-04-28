package com.mapsea.notificationconsumer.feature.vessel.repository

import com.mapsea.notificationconsumer.domain.VesselSetting
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VesselSettingRepository : JpaRepository<VesselSetting, UUID>
