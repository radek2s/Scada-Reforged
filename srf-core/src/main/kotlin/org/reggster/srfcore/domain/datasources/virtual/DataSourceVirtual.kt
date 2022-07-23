package org.reggster.srfcore.domain.datasources.virtual

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
data class DataSourceVirtual (
        @Id
        @GeneratedValue
        var id: Int? = 0,

        var sid: String = "SID_01",

        var name: String = "",

        var updatePeriod: Int = 1,

        var enabled: Boolean = false
        )