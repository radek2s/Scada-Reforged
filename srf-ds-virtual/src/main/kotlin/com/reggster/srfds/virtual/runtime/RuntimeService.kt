package com.reggster.srfds.virtual.runtime

import com.reggster.srfds.virtual.RabbitMessagePublisher
import com.reggster.srfds.virtual.model.DataPointVirtualRT
import com.reggster.srfds.virtual.model.DataSourceVirtualRT
import org.springframework.stereotype.Service

@Service
class RuntimeService(
    private val messagePublisher: RabbitMessagePublisher
) {

    var dataSources: MutableList<DataSourceVirtualRT> = mutableListOf()
    var threads: MutableList<DataSourceThread> = mutableListOf()

    fun addDataSource(ds: DataSourceVirtualRT) {
//        val ds = .setRabbit(messagePublisher)
        dataSources.add(ds.apply { setRabbit(messagePublisher) })
    }

    fun removeDataSource(id: Int) {
        val ds = dataSources.find { it.id == id } ?: return
        ds.interrupt()
        dataSources.remove(ds)
    }

    fun enableAll() {
        dataSources.forEach {
            it.enabled = true
            val t: DataSourceThread = DataSourceThread(it)
            threads.add(t)
            t.start()
//            threads.add(DataSourceThread(it))
//            it.start()
        }
    }

    fun enableById(id: Int) {
        dataSources.find { it.id == id }.also {
            if(it != null && !it.enabled) {
                it.enabled = true
                val th = DataSourceThread(it)

                threads.add(th)
                th.start()

//                threads.add()
//                it.start()
            }
        }
    }

    fun disableById(id: Int) {
        dataSources.find { it.id == id }.also {
            if(it != null) {
                val th = threads.find { th -> th.id == it.id }
                if(th != null) {
                    th.interrupt()
                    threads.remove(th)
                }
                it.enabled = false
            }
        }
    }

    fun getCurrentValueById(id: Int): Int? {
        return dataSources.find { it.id == id }?.value
    }

    fun addDataPoint(dsId: Int, dp: DataPointVirtualRT) {
        dataSources.find { it.id == dsId }.also {
            it?.addDataPoint(dp)
        }
    }

    fun toggleDataPoint(dsId: Int, dpId: Int) {
        dataSources.find { it.id == dsId }.also {
            it?.toggleDataPoint(dpId)
        }
    }

}