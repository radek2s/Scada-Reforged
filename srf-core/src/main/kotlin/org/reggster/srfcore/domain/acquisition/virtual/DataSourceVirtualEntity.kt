package org.reggster.srfcore.domain.acquisition.virtual

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import org.reggster.srfcore.domain.acquisition.ScadaDataSourceEntity
import javax.persistence.Entity

@Entity
class DataSourceVirtualEntity():ScadaDataSourceEntity(ScadaDataSourceType.VIRTUAL), DataSourceVirtual { }