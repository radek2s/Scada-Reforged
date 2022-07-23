package org.reggster.srfcore.domain.datasources.other

import org.reggster.srfcore.domain.datasources.ScadaDataSourceRepository
import org.springframework.stereotype.Repository

@Repository
interface DataSourceOtherRepository: ScadaDataSourceRepository<DataSourceOther, Int>