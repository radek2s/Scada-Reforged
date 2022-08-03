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
abstract class ScadaDataSourceEntity(
    override val type: ScadaDataSourceType,
    @Id @GeneratedValue
    override var id: Int = 0,
    override var sid: String = "",
    override var name: String = "",
    override var enabled: Boolean = false,
    override var updatePeriod: Int = 1,
    override var updatePeriodType: Int = 2
) : ScadaDataSource {

    @Embedded
    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "dp_id")
    @AttributeOverrides(
        AttributeOverride(name="id", column =  Column(name="dp_id"))
    )
    var datapoints: MutableList<DataPointVirtualEntity>? = null

}