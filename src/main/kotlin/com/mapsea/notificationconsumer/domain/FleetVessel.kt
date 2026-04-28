package com.mapsea.notificationconsumer.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment

@Entity
@Table(name = "fleet_vessel")
class FleetVessel(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fleet_id", referencedColumnName = "id")
    var fleet: Fleet? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", referencedColumnName = "id")
    var vessel: Vessel? = null,

    @Comment("선박 표시 여부")
    @Column(name = "is_visible", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    var isVisible: Boolean = true,
) : UUIDAuditable() {

    fun updateIsVisible(isVisible: Boolean) {
        this.isVisible = isVisible
    }

    fun assignToFleet(fleet: Fleet) {
        this.fleet = fleet
    }

    companion object {
        fun createInitVessel(fleet: Fleet, vessel: Vessel): FleetVessel =
            FleetVessel(fleet, vessel, true)
    }
}
