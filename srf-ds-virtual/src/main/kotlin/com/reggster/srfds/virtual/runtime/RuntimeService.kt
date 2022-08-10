package com.reggster.srfds.virtual.runtime

import com.reggster.srfds.virtual.RabbitMessagePublisher
import com.reggster.srfds.virtual.model.DataPointVirtualRT
import com.reggster.srfds.virtual.model.DataSourceVirtualRT
import org.springframework.stereotype.Service

@Service
class RuntimeService(
    private val messagePublisher: RabbitMessagePublisher
) {

    var dataSourcesRT: MutableMap<Int, DataSourceThread> = mutableMapOf()

    fun addDataSource(ds: DataSourceVirtualRT) {
        if(dataSourcesRT[ds.id] == null) {
            dataSourcesRT[ds.id] = DataSourceThread(ds, messagePublisher).apply { this.start() }
        }
    }

    fun removeDataSource(id: Int) {
        val thread = dataSourcesRT[id] ?: return
        thread.interrupt()
        dataSourcesRT.remove(id)
    }

    fun addDataPoint(dsId: Int, dp: DataPointVirtualRT) {
        if(dataSourcesRT[dsId] != null) {
            dataSourcesRT[dsId]?.dataSource?.addDataPoint(dp)
        }
    }

    fun removeDataPoint(dsId: Int, dpId: Int) {
        if(dataSourcesRT[dsId] != null) {
            dataSourcesRT[dsId]?.dataSource?.removeDataPoint(dpId)
        }
    }

    fun setDataPointEnabled(dsId: Int, dpId: Int, enabled: Boolean) {
        if(dataSourcesRT[dsId] != null) {
            dataSourcesRT[dsId]?.dataSource?.setDataPointEnabled(dpId, enabled)
        }
    }



//    fun enableById(id: Int) {
//        dataSourcesRT.find { it.id == id }?.setDataSourceState(true)
//    }
//
//    fun disableById(id: Int) {
//        dataSourcesRT.find { it.id == id }?.setDataSourceState(false)
//    }
//
//    fun enableAll() {
//        dataSourcesRT.forEach { it.start() }
//    }


////        val ds = .setRabbit(messagePublisher)
//        dataSources.add(ds.apply { setRabbit(messagePublisher) })
//    }

//    fun removeDataSource(id: Int) {
//        val ds = dataSources.find { it.id == id } ?: return
//        ds.interrupt()
//        dataSources.remove(ds)
//    }

//    fun enableAll() {
//        dataSources.forEach {
//            it.enabled = true
//            val t: DataSourceThread = DataSourceThread(it)
//            threads.add(t)
//            t.start()
////            threads.add(DataSourceThread(it))
////            it.start()
//        }
//    }

//    fun enableById(id: Int) {
//        dataSources.find { it.id == id }.also {
//            if(it != null && !it.enabled) {
//                it.enabled = true
//                val th = DataSourceThread(it)
//
//                threads.add(th)
//                th.start()
//
////                threads.add()
////                it.start()
//            }
//        }
//    }

//    fun disableById(id: Int) {
//        dataSources.find { it.id == id }.also {
//            if(it != null) {
//                val th = threads.find { th -> th.id == it.id }
//                if(th != null) {
//                    th.interrupt()
//                    threads.remove(th)
//                }
//                it.enabled = false
//            }
//        }
//    }

//    fun getCurrentValueById(id: Int): Int? {
//        return dataSources.find { it.id == id }?.value
//    }
//
//    fun addDataPoint(dsId: Int, dp: DataPointVirtualRT) {
//        dataSources.find { it.id == dsId }.also {
//            it?.addDataPoint(dp)
//        }
//    }
//
//    fun toggleDataPoint(dsId: Int, dpId: Int) {
//        dataSources.find { it.id == dsId }.also {
//            it?.toggleDataPoint(dpId)
//        }
//    }

}