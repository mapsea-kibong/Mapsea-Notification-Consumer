package com.mapsea.notificationconsumer.feature.route_plan.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.mapsea.notificationconsumer.domain.Voyage
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface VoyageRepository : JpaRepository<Voyage, UUID>, KotlinJdslJpqlExecutor
