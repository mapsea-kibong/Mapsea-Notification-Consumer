package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.mapsea.notificationconsumer.domain.RoutePlan
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface RoutePlanRepository : JpaRepository<RoutePlan, UUID>, KotlinJdslJpqlExecutor
