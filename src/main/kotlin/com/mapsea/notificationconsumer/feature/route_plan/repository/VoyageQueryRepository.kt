package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.mapsea.notificationconsumer.domain.Voyage
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
class VoyageQueryRepository(
    private val voyageRepository: VoyageRepository,
) {
    fun findByVoyageNumber(voyageNumber: String): Optional<Voyage> =
        Optional.ofNullable(
            voyageRepository.findAll {
                select(entity(Voyage::class))
                    .from(entity(Voyage::class))
                    .where(path(Voyage::voyageNumber).eq(voyageNumber))
            }.filterNotNull().firstOrNull(),
        )
}
