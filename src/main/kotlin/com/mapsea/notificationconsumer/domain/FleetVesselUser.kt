package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.Comment
import java.util.UUID

@Entity
class FleetVesselUser(
    @Id
    @Column(name = "fleet_vessel_user_id")
    var fleetVesselUserId: UUID = Generators.timeBasedGenerator().generate(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fleet_id", referencedColumnName = "id")
    var fleet: Fleet? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", referencedColumnName = "id")
    var vessel: Vessel? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    var user: User? = null,

    @Comment("선박 표시 여부")
    @Column(name = "is_visible", nullable = false)
    var isVisible: Boolean = true,
) : Auditable() {

    fun updateIsVisible(isVisible: Boolean) {
        this.isVisible = isVisible
    }

    override fun toString(): String =
        "FleetVesselUser(fleetVesselUserId=$fleetVesselUserId, isVisible=$isVisible)"
}
