package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.ScadaDataSourceService
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
abstract class ScadaDataSourceEntityRepositoryImpl<T: ScadaDataSourceEntity, ID: java.io.Serializable>(
    private val scadaDataSourceEntityRepository: ScadaDataSourceEntityRepository<T, ID>
): ScadaDataSourceService<T, ID> {

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT','ROLE_USER')")
    @PostFilter("hasPermission(filterObject, 'READ') or hasRole('ROLE_ADMIN')")
    override fun findAll(): List<T> =
        scadaDataSourceEntityRepository.findAll()

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT','ROLE_USER')")
    override fun findById(entityId: ID): Optional<T> =
        scadaDataSourceEntityRepository.findById(entityId)

    @PreAuthorize("hasPermission(#entity, 'WRITE') or hasAnyRole('ROLE_ADMIN','ROLE_MGMT')")
    override fun save(entity: T): T =
        scadaDataSourceEntityRepository.save(entity)

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT')")
    override fun create(entity: T): T =
        scadaDataSourceEntityRepository.save(entity)

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MGMT')")
    override fun delete(entityId: ID): Unit =
        scadaDataSourceEntityRepository.deleteById(entityId)

}