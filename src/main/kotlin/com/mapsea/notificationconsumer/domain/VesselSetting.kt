package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import com.mapsea.notificationconsumer.domain.enums.DetailedShipType
import com.mapsea.notificationconsumer.domain.enums.DualFuel
import com.mapsea.notificationconsumer.domain.enums.PropulsionTransmissionType
import com.mapsea.notificationconsumer.domain.enums.ShipType
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.LocalDate
import java.util.UUID

@Entity
class VesselSetting(
    @Id
    var vesselSettingId: UUID = Generators.timeBasedGenerator().generate(),

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_vessel_id", nullable = false)
    var companyVessel: CompanyVessel? = null,

    // [1] General Info
    var callsign: String? = null,
    var imo: String? = null,

    @Enumerated(EnumType.STRING)
    var shipType: ShipType? = null,

    @Enumerated(EnumType.STRING)
    var detailedShipType: DetailedShipType? = null,

    var flag: String? = null,
    var portOfRegistry: String? = null,

    // [2] History
    var deliveryDate: LocalDate? = null,
    var purchaseDate: LocalDate? = null,
    var saleDate: LocalDate? = null,

    // [3] Vessel Contact Details
    var masterName: String? = null,
    var ceName: String? = null,
    var vesselCode: String? = null,
    var shipTelephoneNumber: String? = null,
    var shipEmail: String? = null,
    var shipFaxNumber: String? = null,

    // [4] Principal Dimensions & Capacities
    var loa: Double? = null,
    var lbp: Double? = null,
    var breadth: Double? = null,
    var deadWeight: Double? = null,
    var netTonnage: Double? = null,
    var grossTonnage: Double? = null,

    // [5] Propulsion System Specifications
    @Enumerated(EnumType.STRING)
    var propulsionTransmissionType: PropulsionTransmissionType? = null,

    var propellerDiameter: Double? = null,
    var propellerPitch: Double? = null,

    // [6] Bunker Type
    var useLsmgo: Boolean? = null,
    var useMdo: Boolean? = null,
    var useMgo: Boolean? = null,
    var useLsfo: Boolean? = null,
    var useHfo: Boolean? = null,
    var useLng: Boolean? = null,
    var useLpg: Boolean? = null,
    var useHybridFuels: Boolean? = null,
    var useBiofuels: Boolean? = null,

    // [7] Engine & Power Specifications
    var mainEnginePowerAtMCR: Double? = null,
    var mainEngineRpmAtMCR: Double? = null,
    var sfoc: Double? = null,
    var limitedPower: Double? = null,
    var engineType: String? = null,

    @Enumerated(EnumType.STRING)
    var dualFuel: DualFuel? = null,

    var iceClassOfTheShip: Boolean? = null,
    var eedi: Double? = null,
    var estimatedIndexValue: Double? = null,
    var lightWeight: Double? = null,
    var mainPropulsionPower: Double? = null,
    var auxiliaryEnginePower: Double? = null,
) : Auditable()
