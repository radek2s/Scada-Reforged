package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcore.domain.acquisition.virtual.DataPointVirtualEntity
import org.reggster.srfcore.domain.acquisition.virtual.DataSourceVirtualServiceImplEntity
import org.reggster.srfcore.domain.control.DataSourcesRuntimeServices
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
    private val runtimeDsService: DataSourcesRuntimeServices
) {

    fun findAll(): List<ScadaDataSourceEntity<ScadaDataPointEntity>> {
        val dsList: MutableList<ScadaDataSourceEntity<ScadaDataPointEntity>> = mutableListOf()
        getAllServiceBeans().forEach { dsList.addAll(it.findAll()) }
        return dsList.toList()
    }

    @Transactional
    fun save(entity: ScadaDataSourceEntity<ScadaDataPointEntity>, principal: Principal): ScadaDataSourceEntity<ScadaDataPointEntity> =
        getServiceBean(entity.type).save(entity)

    fun findById(entityId: Int, type: ScadaDataSourceType): Optional<ScadaDataSourceEntity<ScadaDataPointEntity>> =
        getServiceBean(type).findById(entityId)

    fun delete(entityId: Int, type: ScadaDataSourceType) =
        getServiceBean(type).delete(entityId).let {
            runtimeDsService.disableDataSource(entityId, type)
        }

    fun create(entity: ScadaDataSourceEntity<ScadaDataPointEntity>, principal: Principal): ScadaDataSourceEntity<ScadaDataPointEntity> =
        getServiceBean(entity.type).create(entity).also { addBasicPermissions(it, principal) }

    fun enable(dsId: Int, type: ScadaDataSourceType) = findById(dsId, type).ifPresent {
        runtimeDsService.enableDataSource(it) //TODO if that will throw an error do not change the state of DS
        it.enabled = true
        getServiceBean(it.type).save(it)
    }
    fun disable(id: Int, type: ScadaDataSourceType) = findById(id, type).ifPresent {
        runtimeDsService.disableDataSource(id, type)
        it.enabled = false
        getServiceBean(it.type).save(it)

    }

    fun createDataPoint(dsId: Int, type: ScadaDataSourceType, entity: DataPointVirtualEntity, principal: Principal): ScadaDataSourceEntity<ScadaDataPointEntity> {
        val ds = findById(dsId, type).get()
        ds.datapoints?.add(entity)
        save(ds, principal)
        try {
            runtimeDsService.addDataPoint(dsId, type, entity)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ds
    }

    fun removeDataPoint(dsId: Int, type: ScadaDataSourceType, dpId: Int, principal: Principal) {
        runtimeDsService.deleteDataPoint(dsId, type, dpId)
        findById(dsId, type).ifPresent {
            val dp = it.datapoints?.find { d -> d.id == dpId }
            if (dp != null) {
                it.datapoints?.remove(dp)
            }
            save(it, principal)
        }
    }

    fun setDataPointState(dsId: Int, type: ScadaDataSourceType, dpId: Int, enabled: Boolean) {
        runtimeDsService.setDataPointState(dsId, type, dpId, enabled)
    }



    fun addRTDataPoints(dsId: Int, type: ScadaDataSourceType) {

    }

    // --- DATA-SOURCES DEFINITIONS :: Extend in this place --- //
    private fun getServiceBean(type: ScadaDataSourceType): ScadaDataSourceEntityService<ScadaDataSourceEntity<ScadaDataPointEntity>> =
        when (type) {
            ScadaDataSourceType.VIRTUAL -> (ctx.getBean(DataSourceVirtualServiceImplEntity::class.java) as ScadaDataSourceEntityService<ScadaDataSourceEntity<ScadaDataPointEntity>>)

//            DataSourceType.OTHER -> ctx.getBean(DataSourceOtherServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
        }

    private fun getAllServiceBeans(): List<ScadaDataSourceEntityService<ScadaDataSourceEntity<ScadaDataPointEntity>>> =
        listOf(
            ctx.getBean(DataSourceVirtualServiceImplEntity::class.java) as ScadaDataSourceEntityService<ScadaDataSourceEntity<ScadaDataPointEntity>>,
//            ctx.getBean(DataSourceOtherServiceImpl::class.java) as ScadaDataSourceService<ScadaDataSource, Int>
        )

    private fun addBasicPermissions(datasource: ScadaDataSourceEntity<ScadaDataPointEntity>, principal: Principal) =
        userService.addPermission(PrincipalSid(principal.name), datasource.javaClass, datasource.id.toLong(), listOf(BasePermission.READ, BasePermission.WRITE))



}