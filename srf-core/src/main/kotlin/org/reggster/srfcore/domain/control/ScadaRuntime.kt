package org.reggster.srfcore.domain.control

import com.reggster.srfds.virtual.model.DataSourceVirtualRT
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
class ScadaRuntime() {

    @Bean
    fun getScadaRuntime(): ScadaRuntime = this

//    var dataSources: MutableList<DataSourceVirtual> = mutableListOf()
    var dataSourceVirtualRT: MutableList<DataSourceVirtualRT> = mutableListOf()

//    @Autowired
//    lateinit var datasourceRepo: DataSourceVirtualRepository

//    fun initDataSources() {
//        this.dataSources = datasourceRepo.findAll()
//    }

//    fun startDataSources() =
//        dataSources.forEach { ds ->
//            dataSourceVirtualRT.add(DataSourceVirtualRT(0.0, false, ds))
//        }.let {
//            dataSourceVirtualRT.forEach(Thread::start)
//        }

//    fun stopDataSources() =
//        dataSourceVirtualRT.forEach(DataSourceVirtualRT::stopDataSource).also {
//            dataSourceVirtualRT.clear()
//        }

}