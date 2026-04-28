package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import java.util.UUID

@Entity
class CompanyVessel(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vessel_id", referencedColumnName = "id")
    var vessel: Vessel? = null,
) : Auditable() {

    @Id
    var companyVesselId: UUID = Generators.timeBasedGenerator().generate()

    @OneToMany(mappedBy = "companyVessel", fetch = FetchType.LAZY)
    var voyages: MutableSet<Voyage> = mutableSetOf()
}
