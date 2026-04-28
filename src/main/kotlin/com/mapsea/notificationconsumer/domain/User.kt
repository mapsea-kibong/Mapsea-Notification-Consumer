package com.mapsea.notificationconsumer.domain

import com.fasterxml.uuid.Generators
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "users")
class User(
    @Id
    @Column(name = "user_id")
    var userId: UUID = Generators.timeBasedGenerator().generate(),

    @Column(name = "email")
    var userEmail: String? = null,

    @Column(name = "name")
    var userName: String? = null,

    @Column(name = "mobile_no")
    var userMobileNumber: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    var company: Company? = null,
) : Auditable() {

    constructor(userId: UUID) : this(
        userId = userId,
        userEmail = null,
        userName = null,
        userMobileNumber = null,
        company = null,
    )
}
