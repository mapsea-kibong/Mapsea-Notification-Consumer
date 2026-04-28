package com.mapsea.notificationconsumer.domain

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "vessel")
class Vessel(
    @Column(unique = true, nullable = false)
    var mmsi: String = "",

    var imo: String? = null,
    var shipname: String? = null,
    var callsign: String? = null,
) : UUIDAuditable() {
    override fun toString(): String =
        "Vessel(mmsi='$mmsi', imo='$imo', shipname='$shipname', callsign='$callsign')"
}
