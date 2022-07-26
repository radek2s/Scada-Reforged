package org.reggster.srfcore.domain.datasources

import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

/**
 * Scada DataSource Repository Implementation
 *
 * Abstract DataSource service for handle common interface
 * for all DataSourceRepositories. It implements Spring-Security AccessControl checks.
 * Only Administrators and Managers can create or delete DataSources
 */
@Service
@Transactional
abstract class ScadaDataSourceRepositoryImpl<T: ScadaDataSource, ID: java.io.Serializable>(
    private val scadaDataSourceRepository: ScadaDataSourceRepository<T, ID>
): ScadaDataSourceService<T, ID> {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT','ROLE_USER')")
    @PostFilter("hasPermission(filterObject, 'READ') or hasRole('ROLE_ADMIN')")
    override fun findAll(): List<T> =
        scadaDataSourceRepository.findAll()

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT','ROLE_USER')")
    @PostFilter("hasPermission(filterObject, 'READ') or hasRole('ROLE_ADMIN')")
    override fun findById(entityId: ID): Optional<T> =
        scadaDataSourceRepository.findById(entityId)

    @PreAuthorize("hasPermission(#entity, 'WRITE') or hasAnyRole('ROLE_ADMIN','ROLE_MGMT')")
    override fun save(entity: T): T =
        scadaDataSourceRepository.save(entity)

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT')")
    override fun create(entity: T): T =
        scadaDataSourceRepository.save(entity)

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT')")
    override fun delete(entityId: ID): Unit =
        scadaDataSourceRepository.deleteById(entityId)

}