package com.mapsea.notificationconsumer.feature.company.repository

import com.mapsea.notificationconsumer.domain.Company
import com.mapsea.notificationconsumer.domain.CompanyVessel
import com.mapsea.notificationconsumer.domain.User
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.feature.vessel.repository.VesselRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
class CompanyQueryRepository(
    private val companyRepository: CompanyRepository,
    private val companyVesselRepository: CompanyVesselRepository,
    private val vesselRepository: VesselRepository,
) {
    fun findCompanyByUser(userId: UUID): Company? =
        companyRepository.findAll {
            select(entity(Company::class))
                .from(
                    entity(User::class),
                    join(User::company),
                )
                .where(path(User::userId).eq(userId))
        }.filterNotNull().firstOrNull()

    fun findCompanyVesselByCompanyIdAndVesselId(companyId: UUID, vesselId: UUID): CompanyVessel? =
        companyVesselRepository.findAll {
            select(entity(CompanyVessel::class))
                .from(
                    entity(CompanyVessel::class),
                    join(CompanyVessel::company),
                    join(CompanyVessel::vessel),
                )
                .where(
                    and(
                        path(Company::companyId).eq(companyId),
                        path(Vessel::id).eq(vesselId),
                    ),
                )
        }.filterNotNull().firstOrNull()

    fun findVesselByCompany(mmsi: String, companyId: UUID): Optional<Vessel> =
        Optional.ofNullable(
            vesselRepository.findAll {
                select(entity(Vessel::class))
                    .from(
                        entity(CompanyVessel::class),
                        join(CompanyVessel::company),
                        join(CompanyVessel::vessel),
                    )
                    .where(
                        and(
                            path(Vessel::mmsi).eq(mmsi),
                            path(Company::companyId).eq(companyId),
                        ),
                    )
            }.filterNotNull().firstOrNull(),
        )
}
