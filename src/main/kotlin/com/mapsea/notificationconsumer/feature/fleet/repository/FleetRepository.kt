package com.mapsea.notificationconsumer.feature.fleet.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.mapsea.notificationconsumer.domain.Fleet
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FleetRepository : JpaRepository<Fleet, UUID>, KotlinJdslJpqlExecutor
