package org.reggster.srfcore.domain.acquisition.virtual


import org.reggster.srfcore.domain.acquisition.ScadaDataSourceEntityRepository
import org.springframework.stereotype.Repository

@Repository
interface DataSourceVirtualEntityRepository : ScadaDataSourceEntityRepository<DataSourceVirtualEntity, Int>