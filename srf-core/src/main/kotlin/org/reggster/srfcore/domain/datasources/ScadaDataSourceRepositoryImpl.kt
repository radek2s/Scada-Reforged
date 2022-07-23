package org.reggster.srfcore.domain.datasources

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
abstract class ScadaDataSourceRepositoryImpl<T: ScadaDataSource, ID: java.io.Serializable>(
    private val scadaDataSourceRepository: ScadaDataSourceRepository<T, ID>
): ScadaDataSourceService<T, ID> {

    override fun findAll(): List<T> =
        scadaDataSourceRepository.findAll()

    override fun findById(entityId: ID): Optional<T> =
        scadaDataSourceRepository.findById(entityId)

    override fun save(entity: T): T =
        scadaDataSourceRepository.save(entity)
}