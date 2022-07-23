package org.reggster.srfcore.domain.datasources.virtual

import org.reggster.srfcore.domain.datasources.ScadaDataSourceRepository
import org.springframework.stereotype.Repository

@Repository
interface DataSourceVirtualRepository : ScadaDataSourceRepository<DataSourceVirtual, Int>