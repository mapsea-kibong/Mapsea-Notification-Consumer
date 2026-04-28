package com.mapsea.notificationconsumer.domain

import jakarta.persistence.Entity
import java.util.UUID

@Entity
class PresentationRoutePlanSync(
    var routePlanId: UUID? = null,
    var presentationId: UUID? = null,
) : UUIDAuditable() {

    companion object {
        fun createPresentationRoutePlanSync(routePlanId: UUID, presentationId: UUID): PresentationRoutePlanSync =
            PresentationRoutePlanSync(routePlanId, presentationId)
    }
}
