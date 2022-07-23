package org.reggster.srfcore.domain.datasources

import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class ScadaDataSource {
    @Id @GeneratedValue
    var id: Int? = 0
    abstract var type: String
    var sid: String = ""
    var name: String = ""
    var enabled: Boolean = false
    var updatePeriod: Int = 1
    var updatePeriodType: Int = 0
}