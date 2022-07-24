package org.reggster.srfcore.domain.datasources.virtual

import org.reggster.srfcore.domain.datasources.DataSourceType
import org.reggster.srfcore.domain.datasources.ScadaDataSource
import javax.persistence.Entity

@Entity
class DataSourceVirtual(
    override var type: DataSourceType = DataSourceType.VIRTUAL
): ScadaDataSource()