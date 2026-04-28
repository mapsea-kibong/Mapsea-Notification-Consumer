package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import com.mapsea.notificationconsumer.domain.enums.MarkerColor
import com.mapsea.notificationconsumer.domain.enums.MarkerIcon
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Comment
import java.util.UUID

@Entity
@Table(name = "marker")
class Marker(
    @Id
    @Column(name = "marker_id")
    var markerId: UUID = Generators.timeBasedGenerator().generate(),

    @Column(name = "marker_name", nullable = false)
    @Comment("마커 이름")
    var markerName: String = "",

    @Column(name = "marker_description")
    @Comment("마커 설명")
    var markerDescription: String? = null,

    @Column(name = "marker_lat")
    @Comment("마커 경도")
    var markerLat: Double? = null,

    @Column(name = "marker_lon")
    @Comment("마커 위도")
    var markerLon: Double? = null,

    @Column(name = "marker_is_visible")
    @Comment("마커 표시 여부")
    var markerIsVisible: Boolean? = null,

    @Column(name = "marker_is_active", columnDefinition = "boolean default true")
    @Comment("마커 표시 여부")
    var markerIsActive: Boolean? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "marker_icon", nullable = false)
    @Comment("마커 아이콘")
    var markerIcon: MarkerIcon? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "marker_color", nullable = false)
    @Comment("마커 아이콘")
    var markerColor: MarkerColor? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company? = null,
) : Auditable() {

    fun updateMarkerIsVisible(markerIsVisible: Boolean?) {
        this.markerIsVisible = markerIsVisible
    }

    fun deleteMarker() {
        this.markerIsActive = false
    }
}
