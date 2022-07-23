package org.reggster.srfcore.domain.datasources

import java.util.*

interface ScadaDataSourceService<T: ScadaDataSource, ID: java.io.Serializable> {

    abstract fun findAll(): List<T>

    abstract fun findById(entityId: ID): Optional<T>

    abstract fun save(entity: T): T

}