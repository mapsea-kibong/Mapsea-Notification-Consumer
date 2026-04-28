package com.mapsea.notificationconsumer.domain

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class Voyage(
    var voyageNumber: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_vessel_id")
    var companyVessel: CompanyVessel? = null,
) : UUIDAuditable() {

    @OneToOne(mappedBy = "voyage")
    var routePlan: RoutePlan? = null

    fun updateVoyageNumber(newVoyageNumber: String) {
        this.voyageNumber = newVoyageNumber
    }

    companion object {
        fun createVoyage(voyageNumber: String, companyVessel: CompanyVessel): Voyage =
            Voyage(voyageNumber, companyVessel)

        fun copyVoyage(voyage: Voyage, companyVessel: CompanyVessel): Voyage =
            Voyage(voyage.voyageNumber, companyVessel)
    }
}
