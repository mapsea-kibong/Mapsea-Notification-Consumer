package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import java.util.UUID

@MappedSuperclass
abstract class UUIDAuditable : Auditable() {

    @Id
    var id: UUID? = null

    @PrePersist
    fun initializeUUID() {
        if (id == null) {
            id = Generators.timeBasedGenerator().generate()
        }
    }
}
