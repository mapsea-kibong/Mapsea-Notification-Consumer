package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.linecorp.kotlinjdsl.dsl.jpql.jpql
import com.mapsea.notificationconsumer.domain.Voyage
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class VoyageQueryRepository(
    private val voyageRepository: VoyageRepository,
) {
    fun findByVoyageNumber(voyageNumber: String): Optional<Voyage> =
        Optional.ofNullable(
            voyageRepository.findAll(
                jpql {
                    select(entity(Voyage::class))
                        .from(entity(Voyage::class))
                        .where(path(Voyage::voyageNumber).eq(voyageNumber))
                },
            ).firstOrNull(),
        )
}
