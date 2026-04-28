package com.mapsea.notificationconsumer.feature.company.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
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
        companyRepository.findAll(
            jpql {
                select(entity(Company::class))
                    .from(
                        entity(User::class),
                        join(User::company),
                    )
                    .where(path(User::userId).eq(userId))
            },
        ).firstOrNull()

    fun findCompanyVesselByCompanyIdAndVesselId(companyId: UUID, vesselId: UUID): CompanyVessel? =
        companyVesselRepository.findAll(
            jpql {
                select(entity(CompanyVessel::class))
                    .from(
                        entity(CompanyVessel::class),
                        join(CompanyVessel::company),
                    )
                    .where(
                        and(
                            path(Company::companyId).eq(companyId),
                            path(CompanyVessel::vessel, Vessel::id).eq(vesselId),
                        ),
                    )
            },
        ).firstOrNull()

    fun findVesselByCompany(mmsi: String, companyId: UUID): Optional<Vessel> =
        Optional.ofNullable(
            vesselRepository.findAll(
                jpql {
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
                },
            ).firstOrNull(),
        )
}
