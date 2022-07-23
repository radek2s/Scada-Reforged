package org.reggster.srfcore.domain.datasources.virtual

import org.springframework.stereotype.Component
import java.util.Optional

@Component
class DataSourceVirtualService(
    private val dsRepository: DataSourceVirtualRepository
) {
    fun findById(id: Int): Optional<DataSourceVirtual> = dsRepository.findById(id)

    fun save(ds: DataSourceVirtual) = dsRepository.save(ds)
}