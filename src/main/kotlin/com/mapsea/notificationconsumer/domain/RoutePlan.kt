package com.mapsea.notificationconsumer.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import jakarta.persistence.Table
import org.hibernate.annotations.BatchSize
import java.time.Instant

@Entity
@Table(name = "route_plan")
class RoutePlan(
    var departureTime: Instant? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "voyage_id", referencedColumnName = "id")
    var voyage: Voyage? = null,

    @Column(nullable = false)
    var isActive: Boolean = true,
) : UUIDAuditable() {

    @OneToMany(mappedBy = "routePlan", cascade = [CascadeType.ALL])
    @BatchSize(size = 10)
    val wayPoints: MutableList<WayPoint> = mutableListOf()

    @OneToMany(mappedBy = "routePlan", cascade = [CascadeType.ALL])
    val routeNotifications: MutableList<RouteNotification> = mutableListOf()

    fun addWayPoint(wayPoint: WayPoint) {
        wayPoints.add(wayPoint)
    }

    fun deleteRoutePlan() {
        this.isActive = false
    }

    fun updateRoutePlan(compareRoutePlan: RoutePlan) {
        if (this.departureTime != compareRoutePlan.departureTime) {
            this.departureTime = compareRoutePlan.departureTime
        }
    }

    fun updateCompareRoutePlan(compareRoutePlan: RoutePlan) {
        if (this.departureTime != compareRoutePlan.departureTime) {
            this.departureTime = compareRoutePlan.departureTime
        }
        if (this.isActive != compareRoutePlan.isActive) {
            this.isActive = compareRoutePlan.isActive
        }
    }

    companion object {
        fun createRoutePlan(departureTime: Instant?, voyage: Voyage?, isActive: Boolean): RoutePlan =
            RoutePlan(departureTime, voyage, isActive)

        fun copyRoutePlan(routePlan: RoutePlan, voyage: Voyage?): RoutePlan =
            RoutePlan(routePlan.departureTime, voyage, routePlan.isActive)
    }
}
