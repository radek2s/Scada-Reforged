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

}