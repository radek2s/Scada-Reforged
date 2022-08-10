package org.reggster.srfcore.domain.acquisition.virtual

import org.reggster.srfcommons.acquisition.DataPointType
import org.reggster.srfcommons.acquisition.virtual.DataPointVirtual
import org.reggster.srfcore.domain.acquisition.ScadaDataPointEntity
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class DataPointVirtualEntity(
    @Id @GeneratedValue
    override var id: Int = 0,
    override var sid: String = "",
    override var name: String = "",
    override var enabled: Boolean = false,
    override var settable: Boolean = false,
    override var dataType: DataPointType = DataPointType.BINARY,
    override var changeType: Int = 0,
    override var minValue: Int = 0,
    override var maxValue: Int = 100,
) : ScadaDataPointEntity(id, sid, name, enabled, settable, dataType), DataPointVirtual { }