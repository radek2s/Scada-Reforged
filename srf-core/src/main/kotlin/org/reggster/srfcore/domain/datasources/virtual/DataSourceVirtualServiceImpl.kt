package org.reggster.srfcore.domain.datasources.virtual

import org.reggster.srfcore.domain.datasources.ScadaDataSourceRepositoryImpl
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class DataSourceVirtualServiceImpl(
    private val dsRepository: DataSourceVirtualRepository
): ScadaDataSourceRepositoryImpl<DataSourceVirtual, Int>(dsRepository), DataSourceVirtualService {

    override fun findById(entityId: Int): Optional<DataSourceVirtual> =
        dsRepository.findById(entityId)

    override fun save(entity: DataSourceVirtual) =
        dsRepository.save(entity)

    override fun findAll(): List<DataSourceVirtual> =
        dsRepository.findAll()
}