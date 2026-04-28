package com.mapsea.notificationconsumer.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "company")
class Company(
    @Id
    @Column(name = "company_id")
    var companyId: UUID? = null,

    @Column(name = "company_name")
    var companyName: String? = null,

    @Column(name = "max_vessels")
    var maxVessels: Int = 0,

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var markerList: MutableList<Marker> = mutableListOf(),
) : Auditable() {

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    val fleets: MutableList<Fleet> = mutableListOf()

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var companyVessels: MutableList<CompanyVessel> = mutableListOf()

    @OneToMany(mappedBy = "company", fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    var userList: MutableList<User> = mutableListOf()

    constructor(companyName: String?, maxVessels: Int) : this(
        companyName = companyName,
        maxVessels = maxVessels,
    ) {
        Fleet.addDefaultFleetToCompany(this)
    }

    companion object {
        const val MAX_FLEETS: Int = 3

        fun initCompany(companyId: UUID, companyName: String): Company =
            Company(companyId = companyId, companyName = companyName)
    }
}
