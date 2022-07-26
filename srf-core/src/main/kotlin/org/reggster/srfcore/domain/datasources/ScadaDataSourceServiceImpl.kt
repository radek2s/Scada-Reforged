package org.reggster.srfcore.domain.datasources

import org.reggster.srfcore.domain.datasources.other.DataSourceOtherServiceImpl
import org.reggster.srfcore.domain.datasources.virtual.DataSourceVirtualServiceImpl
import org.reggster.srfcore.security.acl.ScadaUserService
import org.springframework.context.ApplicationContext
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal
import java.util.*

@Service
class ScadaDataSourceServiceImpl(
    val ctx: ApplicationContext,
    private val userService: ScadaUserService
) {

    fun findAll(): List<ScadaDataSource> {
        val dsList: MutableList<ScadaDataSource> = mutableListOf()
        getAllServiceBeans().forEach { dsList.addAll(it.findAll()) }
        return dsList.toList()
    }

    @Transactional
    fun save(entity: ScadaDataSource, principal: Principal): ScadaDataSource =
        getServiceBean(entity.type).save(entity)

    fun findById(entityId: Int, type: DataSourceType): Optional<ScadaDataSource> =
        getServiceBean(type).findById(entityId)

    fun delete(entityId: Int, type: DataSourceType) =
        getServiceBean(type).delete(entityId)

    fun create(entity: ScadaDataSource, principal: Principal): ScadaDataSource =
        getServiceBean(entity.type).create(entity).also { addBasicPermissions(it, principal) }

    // --- DATA-SOURCES DEFINITIONS :: Extend in this place --- //
    private fun getServiceBean(type: DataSourceType): ScadaDataSourceService<ScadaDataSource, Int> =
        when (type) {
            DataSourceType.VIRTUAL -> ctx.getBean(DataSourceVirtualServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
            DataSourceType.OTHER -> ctx.getBean(DataSourceOtherServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
        }

    private fun getAllServiceBeans(): List<ScadaDataSourceService<ScadaDataSource, Int>> =
        listOf(
            ctx.getBean(DataSourceVirtualServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>,
            ctx.getBean(DataSourceOtherServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
        )

    private fun addBasicPermissions(datasource: ScadaDataSource, principal: Principal) =
        userService.addPermission(PrincipalSid(principal.name), datasource.javaClass, datasource.id.toLong(), listOf(BasePermission.READ, BasePermission.WRITE))

}