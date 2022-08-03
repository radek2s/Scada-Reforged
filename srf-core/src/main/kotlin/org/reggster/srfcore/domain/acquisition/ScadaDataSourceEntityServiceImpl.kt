package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcore.domain.acquisition.virtual.DataPointVirtualEntity
import org.reggster.srfcore.domain.acquisition.virtual.DataSourceVirtualEntity
import org.reggster.srfcore.domain.acquisition.virtual.DataSourceVirtualServiceImplEntity
import org.reggster.srfcore.security.acl.ScadaUserService
import org.springframework.context.ApplicationContext
import org.springframework.security.acls.domain.BasePermission
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.security.Principal
import java.util.*

@Service
class ScadaDataSourceEntityServiceImpl(
    val ctx: ApplicationContext,
    private val userService: ScadaUserService,
    private val runtimeDsService: RuntimeDataSourceServices
) {

    fun findAll(): List<ScadaDataSourceEntity> {
        val dsList: MutableList<ScadaDataSourceEntity> = mutableListOf()
        getAllServiceBeans().forEach { dsList.addAll(it.findAll()) }
        return dsList.toList()
    }

    @Transactional
    fun save(entity: ScadaDataSourceEntity, principal: Principal): ScadaDataSourceEntity =
        getServiceBean(entity.type).save(entity)

    fun findById(entityId: Int, type: ScadaDataSourceType): Optional<ScadaDataSourceEntity> =
        getServiceBean(type).findById(entityId)

    fun delete(entityId: Int, type: ScadaDataSourceType) =
        getServiceBean(type).delete(entityId)

    fun create(entity: ScadaDataSourceEntity, principal: Principal): ScadaDataSourceEntity =
        getServiceBean(entity.type).create(entity).also { addBasicPermissions(it, principal) }

    fun createDataPoint(dsId: Int, type: ScadaDataSourceType, entity: DataPointVirtualEntity, principal: Principal): ScadaDataSourceEntity {
        val ds = findById(dsId, type).get()
        ds.datapoints?.add(entity)
        save(ds, principal)
        return ds
    }

    fun initRT(dsId: Int, type: ScadaDataSourceType) {
        val ds = findById(dsId, type).get()
        runtimeDsService.initDataSource(ds as DataSourceVirtualEntity)
    }

    fun addRTDataPoints(dsId: Int, type: ScadaDataSourceType) {

    }

    // --- DATA-SOURCES DEFINITIONS :: Extend in this place --- //
    private fun getServiceBean(type: ScadaDataSourceType): ScadaDataSourceEntityService<ScadaDataSourceEntity> =
        when (type) {
            ScadaDataSourceType.VIRTUAL -> (ctx.getBean(DataSourceVirtualServiceImplEntity::class.java) as ScadaDataSourceEntityService<ScadaDataSourceEntity>)

//            DataSourceType.OTHER -> ctx.getBean(DataSourceOtherServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
        }

    private fun getAllServiceBeans(): List<ScadaDataSourceEntityService<ScadaDataSourceEntity>> =
        listOf(
            ctx.getBean(DataSourceVirtualServiceImplEntity::class.java) as ScadaDataSourceEntityService<ScadaDataSourceEntity>,
//            ctx.getBean(DataSourceOtherServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
        )

    private fun addBasicPermissions(datasource: ScadaDataSourceEntity, principal: Principal) =
        userService.addPermission(PrincipalSid(principal.name), datasource.javaClass, datasource.id.toLong(), listOf(BasePermission.READ, BasePermission.WRITE))



}