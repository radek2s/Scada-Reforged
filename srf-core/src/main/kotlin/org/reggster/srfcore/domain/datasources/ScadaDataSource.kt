package org.reggster.srfcore.domain.datasources

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.reggster.srfcore.domain.datasources.other.DataSourceOther
import org.reggster.srfcore.domain.datasources.virtual.DataSourceVirtual
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

enum class DataSourceType {
    VIRTUAL, OTHER
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = DataSourceVirtual::class, name = "VIRTUAL"),
    JsonSubTypes.Type(value = DataSourceOther::class, name = "OTHER")
)
@MappedSuperclass
abstract class ScadaDataSource {
    @Id @GeneratedValue
    var id: Int? = 0
    abstract var type: DataSourceType
    var sid: String = ""
    var name: String = ""
    var enabled: Boolean = false
    var updatePeriod: Int = 1
    var updatePeriodType: Int = 0
}