package com.reggster.srfds.virtual.runtime

import com.reggster.srfds.virtual.model.DataPointVirtualRT
import com.reggster.srfds.virtual.model.DataSourceVirtualRT
import org.springframework.stereotype.Service

@Service
class RuntimeService {

    var dataSources: MutableList<DataSourceVirtualRT> = mutableListOf()

    fun addDataSource(ds: DataSourceVirtualRT) {
        dataSources.add(ds)
    }

    fun removeDataSource(id: Int) {
        val ds = dataSources.find { it.id == id } ?: return
        ds.interrupt()
        dataSources.remove(ds)
    }

    fun enableAll() {
        dataSources.forEach {
            it.enabled = true
            it.start()
        }
    }

    fun enableById(id: Int) {
        dataSources.find { it.id == id }.also {
            if(it != null) {
                it.enabled = true
                it.start()
            }
        }
    }

    fun disableById(id: Int) {
        dataSources.find { it.id == id }.also {
            if(it != null) {
                it.enabled = false
                it.interrupt()
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