package org.reggster.srfcore.domain.datasources.other

import org.reggster.srfcore.domain.datasources.DataSourceType
import org.reggster.srfcore.domain.datasources.ScadaDataSource
import javax.persistence.Entity

@Entity
class DataSourceOther(
    override var type: DataSourceType = DataSourceType.OTHER,
    var location: String = ""
): ScadaDataSource()