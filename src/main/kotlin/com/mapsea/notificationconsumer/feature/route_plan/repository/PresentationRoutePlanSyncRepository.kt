package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.mapsea.notificationconsumer.domain.PresentationRoutePlanSync
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface PresentationRoutePlanSyncRepository : JpaRepository<PresentationRoutePlanSync, UUID> {
    fun findPresentationRoutePlanSyncByRoutePlanId(routePlanId: UUID): Optional<PresentationRoutePlanSync>
}
