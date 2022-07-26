package org.reggster.srfcore.domain.datasources.virtual

import org.reggster.srfcore.domain.datasources.ScadaDataSourceRepositoryImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DataSourceVirtualServiceImpl(
    dsRepository: DataSourceVirtualRepository
): ScadaDataSourceRepositoryImpl<DataSourceVirtual, Int>(dsRepository), DataSourceVirtualService {}