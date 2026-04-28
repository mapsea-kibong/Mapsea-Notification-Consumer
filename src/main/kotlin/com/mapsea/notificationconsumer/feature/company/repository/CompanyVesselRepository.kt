package com.mapsea.notificationconsumer.feature.company.repository

import com.linecorp.kotlinjdsl.support.spring.data.jpa.repository.KotlinJdslJpqlExecutor
import com.mapsea.notificationconsumer.domain.CompanyVessel
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface CompanyVesselRepository : JpaRepository<CompanyVessel, UUID>, KotlinJdslJpqlExecutor
