package org.reggster.srfcore.domain.acquisition

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.reggster.srfcommons.acquisition.ScadaDataSource
import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcore.domain.acquisition.virtual.DataPointVirtualEntity
import org.reggster.srfcore.domain.acquisition.virtual.DataSourceVirtualEntity
import javax.persistence.*


@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(value = DataSourceVirtualEntity::class, name = "VIRTUAL"),
//    JsonSubTypes.Type(value = DataSourceOther::class, name = "OTHER")
)
@MappedSuperclass
@Inheritance(strategy = InheritanceType.JOINED)
abstract class ScadaDataSourceEntity<T : ScadaDataPointEntity>(
    override val type: ScadaDataSourceType,
    @Id @GeneratedValue
    override var id: Int = 0,
    override var sid: String = "",
    override var name: String = "",
    override var enabled: Boolean = false,
    override var updatePeriod: Int = 1,
    override var updatePeriodType: Int = 2
) : ScadaDataSource {

    abstract var datapoints: MutableList<T>?

}