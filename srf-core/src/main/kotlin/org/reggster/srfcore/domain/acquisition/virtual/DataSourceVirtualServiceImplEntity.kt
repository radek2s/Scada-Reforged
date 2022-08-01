package org.reggster.srfcore.domain.acquisition.virtual

import org.reggster.srfcore.domain.acquisition.ScadaDataSourceEntityRepositoryImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DataSourceVirtualServiceImplEntity(
    dsRepository: DataSourceVirtualEntityRepository
): ScadaDataSourceEntityRepositoryImpl<DataSourceVirtualEntity, Int>(dsRepository), DataSourceVirtualService {}