package org.reggster.srfcore.domain.datasources.other

import org.reggster.srfcore.domain.datasources.ScadaDataSourceRepositoryImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class DataSourceOtherServiceImpl(
    private val dsRepository: DataSourceOtherRepository
): ScadaDataSourceRepositoryImpl<DataSourceOther, Int>(dsRepository), DataSourceOtherService