package org.reggster.srfcommons.acquisition

import java.util.*

interface ScadaDataSourceService<T: ScadaDataSource, ID: java.io.Serializable> {

    fun findAll(): List<T>

    fun findById(entityId: ID): Optional<T>

    fun save(entity: T): T

    fun create(entity: T): T

    fun delete(entityId: ID)

}