package org.reggster.srfcore.domain.datasources

import org.reggster.srfcore.domain.datasources.other.DataSourceOther
import org.reggster.srfcore.domain.datasources.other.DataSourceOtherServiceImpl
import org.reggster.srfcore.domain.datasources.virtual.DataSourceVirtual
import org.reggster.srfcore.domain.datasources.virtual.DataSourceVirtualServiceImpl
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Service
import java.util.*

@Service
class ScadaDataSourceServiceImpl(
    val ctx: ApplicationContext
): ScadaDataSourceService<ScadaDataSource, Int> {

    //ToDo: Create a factory method and get rid of sub-controller
    // Factory method should decide which Service to use based on DS "type"
    // Generic method for findAll, getOne, save, update, etc.
    // More sophisticated methods should be implemented per datasource
    override fun findAll(): List<ScadaDataSource> {
        val dsList: MutableList<ScadaDataSource> = mutableListOf()
        dsList.addAll(ctx.getBean(DataSourceVirtualServiceImpl::class.java).findAll())
        dsList.addAll(ctx.getBean(DataSourceOtherServiceImpl::class.java).findAll())

        return dsList.toList()
    }

    override fun save(entity: ScadaDataSource): ScadaDataSource =
        when (entity.type) {
            DataSourceType.VIRTUAL
            -> ctx.getBean(DataSourceVirtualServiceImpl::class.java).save(entity as DataSourceVirtual)
            DataSourceType.OTHER
            -> ctx.getBean(DataSourceOtherServiceImpl::class.java).save(entity as DataSourceOther)
        }

    fun findById(entityId: Int, type: DataSourceType): Optional<ScadaDataSource> =
        when (type) {
            DataSourceType.VIRTUAL
                -> ctx.getBean(DataSourceVirtualServiceImpl::class.java).findById(entityId) as Optional<ScadaDataSource>
            DataSourceType.OTHER
                -> ctx.getBean(DataSourceOtherServiceImpl::class.java).findById(entityId) as Optional<ScadaDataSource>
    }

    override fun findById(entityId: Int): Optional<ScadaDataSource> {
        TODO("Not yet implemented")
    }
}