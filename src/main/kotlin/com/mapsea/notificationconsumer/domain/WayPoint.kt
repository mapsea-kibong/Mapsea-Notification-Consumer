package com.mapsea.notificationconsumer.domain

import com.mapsea.notificationconsumer.domain.enums.SailMethod
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import jakarta.persistence.UniqueConstraint
import org.springframework.data.geo.Point
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(
            columnNames = ["route_plan_id", "order_index"],
            name = "UK_route_plan_orderIndex",
        ),
    ],
)
class WayPoint(
    var coordinate: Point? = null,

    @Column(precision = 10, scale = 6)
    var longitude: BigDecimal? = null,

    @Column(precision = 10, scale = 6)
    var latitude: BigDecimal? = null,

    var orderIndex: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_plan_id", referencedColumnName = "id")
    var routePlan: RoutePlan? = null,

    var speed: BigDecimal? = null,
    var rateOfTurn: BigDecimal? = null,
    var turnRadius: BigDecimal? = null,
    var timeZone: String? = null,
    var name: String? = null,
    var portDistance: BigDecimal? = null,
    var starboardDistance: BigDecimal? = null,
    var arrivalRadius: BigDecimal? = null,

    @Enumerated(EnumType.STRING)
    var sailMethod: SailMethod? = null,

    var eta: LocalDateTime? = null,
    var distance: BigDecimal? = null,
    var bww: BigDecimal? = null,
    var towl: String? = null,
) : UUIDAuditable() {

    fun updateWayPointByWayPoint(updateWayPoint: WayPoint) {
        this.coordinate = updateWayPoint.coordinate
        this.latitude = updateWayPoint.latitude
        this.longitude = updateWayPoint.longitude
        this.orderIndex = updateWayPoint.orderIndex
        this.speed = updateWayPoint.speed
        this.rateOfTurn = updateWayPoint.rateOfTurn
        this.turnRadius = updateWayPoint.turnRadius
        this.timeZone = updateWayPoint.timeZone
        this.name = updateWayPoint.name
        this.portDistance = updateWayPoint.portDistance
        this.starboardDistance = updateWayPoint.starboardDistance
        this.arrivalRadius = updateWayPoint.arrivalRadius
        this.sailMethod = updateWayPoint.sailMethod
        this.eta = updateWayPoint.eta
        this.distance = updateWayPoint.distance
        this.bww = updateWayPoint.bww
        this.towl = updateWayPoint.towl
    }

    companion object {
        fun copyWayPoint(routePlan: RoutePlan, wayPoint: WayPoint): WayPoint = WayPoint(
            coordinate = wayPoint.coordinate,
            latitude = wayPoint.latitude,
            longitude = wayPoint.longitude,
            orderIndex = wayPoint.orderIndex,
            routePlan = routePlan,
            speed = wayPoint.speed,
            rateOfTurn = wayPoint.rateOfTurn,
            turnRadius = wayPoint.turnRadius,
            timeZone = wayPoint.timeZone,
            name = wayPoint.name,
            portDistance = wayPoint.portDistance,
            starboardDistance = wayPoint.starboardDistance,
            arrivalRadius = wayPoint.arrivalRadius,
            sailMethod = wayPoint.sailMethod,
            eta = wayPoint.eta,
            distance = wayPoint.distance,
            bww = wayPoint.bww,
            towl = wayPoint.towl,
        )
    }
}
