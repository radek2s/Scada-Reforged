package org.reggster.srfcore.domain.datasources

import org.reggster.srfcore.domain.datasources.other.DataSourceOtherServiceImpl
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
        var dsv = ctx.getBean(DataSourceVirtualServiceImpl::class.java)
        dsv.findAll().let {
            dsList.addAll(it)
        }
        var dsv2 = ctx.getBean(DataSourceOtherServiceImpl::class.java)
        dsv2.findAll().let {
            dsList.addAll(it)
        }


        return dsList.toList()
    }

    override fun save(entity: ScadaDataSource): ScadaDataSource {
        TODO("Not yet implemented")
    }

    override fun findById(entityId: Int): Optional<ScadaDataSource> {
        TODO("Not yet implemented")
    }
}