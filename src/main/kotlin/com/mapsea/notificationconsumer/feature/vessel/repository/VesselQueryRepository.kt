package com.mapsea.notificationconsumer.feature.vessel.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.mapsea.notificationconsumer.domain.Company
import com.mapsea.notificationconsumer.domain.CompanyVessel
import com.mapsea.notificationconsumer.domain.Vessel
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class VesselQueryRepository(
    private val vesselRepository: VesselRepository,
) {
    fun findVesselByCompanyId(companyId: UUID): List<Vessel> =
        vesselRepository.findAll(
            jpql {
                select(entity(Vessel::class))
                    .from(
                        entity(Company::class),
                        join(Company::companyVessels),
                        join(CompanyVessel::vessel),
                    )
                    .where(path(Company::companyId).eq(companyId))
            },
        ).filterNotNull()
}
