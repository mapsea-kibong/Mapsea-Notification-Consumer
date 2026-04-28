package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.mapsea.notificationconsumer.domain.Company
import com.mapsea.notificationconsumer.domain.CompanyVessel
import com.mapsea.notificationconsumer.domain.RoutePlan
import com.mapsea.notificationconsumer.domain.UUIDAuditable
import com.mapsea.notificationconsumer.domain.Vessel
import com.mapsea.notificationconsumer.domain.Voyage
import com.mapsea.notificationconsumer.domain.WayPoint
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
class RoutePlanQueryRepository(
    private val routePlanRepository: RoutePlanRepository,
) {
    fun routePlanListInUUID(routePlanIdSet: Set<UUID>): List<RoutePlan> =
        routePlanRepository.findAll(
            jpql {
                select(entity(RoutePlan::class))
                    .from(entity(RoutePlan::class))
                    .where(path(RoutePlan::id).`in`(routePlanIdSet))
            },
        ).filterNotNull()

    fun routePlanList(companyId: UUID, vesselId: UUID): List<RoutePlan> =
        routePlanRepository.findAll(
            jpql {
                select(entity(RoutePlan::class))
                    .from(
                        entity(RoutePlan::class),
                        fetchJoin(RoutePlan::voyage),
                        fetchJoin(Voyage::companyVessel),
                        join(CompanyVessel::company),
                        join(CompanyVessel::vessel),
                        fetchJoin(RoutePlan::wayPoints),
                    )
                    .where(
                        and(
                            path(Company::companyId).eq(companyId),
                            path(Vessel::id).eq(vesselId),
                        ),
                    )
            },
        ).filterNotNull()

    fun findRoutePlanById(routePlanId: UUID): RoutePlan? =
        routePlanRepository.findAll(
            jpql {
                select(entity(RoutePlan::class))
                    .from(
                        entity(RoutePlan::class),
                        fetchJoin(RoutePlan::voyage),
                        fetchJoin(Voyage::companyVessel),
                        fetchJoin(RoutePlan::wayPoints),
                    )
                    .where(path(RoutePlan::id).eq(routePlanId))
            },
        ).firstOrNull()
}
