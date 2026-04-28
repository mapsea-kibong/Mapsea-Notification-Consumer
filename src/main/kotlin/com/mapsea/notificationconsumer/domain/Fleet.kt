package com.mapsea.notificationconsumer.domain

import com.fasterxml.jackson.annotation.JsonBackReference
import com.mapsea.notificationconsumer.domain.enums.fleet.FleetColor
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import org.hibernate.annotations.BatchSize

@Entity
@Table(name = "fleet")
class Fleet(
    @Column(name = "fleet_name", nullable = false)
    var fleetName: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonBackReference
    var company: Company? = null,

    @Column(name = "fleet_color")
    @Enumerated(EnumType.STRING)
    var fleetColor: FleetColor? = null,
) : UUIDAuditable() {

    @OneToMany(mappedBy = "fleet", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @BatchSize(size = 10)
    val fleetVessels: MutableList<FleetVessel> = mutableListOf()

    @OneToMany(mappedBy = "fleet", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val fleetVesselUsers: MutableList<FleetVesselUser> = mutableListOf()

    fun updateFleetInfo(fleetName: String, fleetColor: FleetColor?) {
        this.fleetName = fleetName
        this.fleetColor = fleetColor
    }

    internal fun assignToCompany(company: Company) {
        this.company = company
        company.fleets.add(this)
    }

    companion object {
        const val DEFAULT_FLEET_NAME: String = "Fleet1"

        fun addDefaultFleetToCompany(company: Company) {
            if (company.fleets.isEmpty()) {
                Fleet(DEFAULT_FLEET_NAME, company, FleetColor.RED)
                    .assignToCompany(company)
            }
        }
    }
}
