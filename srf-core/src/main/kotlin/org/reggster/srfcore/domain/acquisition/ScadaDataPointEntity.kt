package org.reggster.srfcore.domain.acquisition

import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.reggster.srfcommons.acquisition.DataPointType
import org.reggster.srfcommons.acquisition.ScadaDataPoint
import javax.persistence.*

@MappedSuperclass
abstract class ScadaDataPointEntity(
    @Id @GeneratedValue
    override var id: Int,
    override var sid: String,
    override var name: String,
    override var enabled: Boolean,
    override var settable: Boolean,
    override var dataType: DataPointType
) : ScadaDataPoint { }