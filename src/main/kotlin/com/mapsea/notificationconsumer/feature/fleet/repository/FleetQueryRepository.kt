package com.mapsea.notificationconsumer.feature.fleet.repository

import com.mapsea.notificationconsumer.domain.Company
import com.mapsea.notificationconsumer.domain.Fleet
import com.mapsea.notificationconsumer.domain.FleetVessel
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class FleetQueryRepository(
    private val fleetRepository: FleetRepository,
) {
    fun getMyFleetVesselByCompanyId(companyId: UUID): List<Fleet> =
        fleetRepository.findAll {
            selectDistinct(entity(Fleet::class))
                .from(
                    entity(Fleet::class),
                    join(Fleet::company),
                    fetchJoin(Fleet::fleetVessels),
                    fetchJoin(FleetVessel::vessel),
                )
                .where(path(Company::companyId).eq(companyId))
        }.filterNotNull()

    fun getMyFleetByCompanyId(companyId: UUID): List<Fleet> =
        fleetRepository.findAll {
            select(entity(Fleet::class))
                .from(
                    entity(Fleet::class),
                    join(Fleet::company),
                )
                .where(path(Company::companyId).eq(companyId))
        }.filterNotNull()
}
