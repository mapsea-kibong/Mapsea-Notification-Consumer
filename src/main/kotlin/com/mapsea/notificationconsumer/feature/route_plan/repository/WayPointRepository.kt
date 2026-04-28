package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.mapsea.notificationconsumer.domain.WayPoint
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WayPointRepository : JpaRepository<WayPoint, UUID>
