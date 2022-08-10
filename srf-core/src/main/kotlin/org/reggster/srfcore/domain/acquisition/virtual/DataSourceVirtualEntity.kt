package org.reggster.srfcore.domain.acquisition.virtual

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import org.reggster.srfcore.domain.acquisition.ScadaDataSourceEntity
import javax.persistence.*

@Entity
class DataSourceVirtualEntity():ScadaDataSourceEntity<DataPointVirtualEntity>(ScadaDataSourceType.VIRTUAL), DataSourceVirtual {

    @Embedded
    @ElementCollection(fetch = FetchType.EAGER)
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "dp_id")
    @AttributeOverrides(
        AttributeOverride(name="id", column =  Column(name="dp_id"))
    )
    override var datapoints: MutableList<DataPointVirtualEntity>? = null

}