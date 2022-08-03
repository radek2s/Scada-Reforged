package com.reggster.srfds.virtual.model

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual

class DataSourceVirtualRT(
    override val type: ScadaDataSourceType,
    override var id: Int,
    override var sid: String,
    override var name: String,
    override var enabled: Boolean,
    override var updatePeriod: Int,
    override var updatePeriodType: Int,
    var datapoints: MutableList<DataPointVirtualRT> = mutableListOf(),
    var value: Int
) : DataSourceVirtual, Runnable {
    private lateinit var worker: Thread


    fun interrupt() {
        enabled = false
        worker.interrupt()
    }

    fun start() {
        worker = Thread(this)
        worker.start()
    }

    fun addDataPoint(dp: DataPointVirtualRT) {
        datapoints.add(dp)
    }

    fun toggleDataPoint(dpId: Int) {
        datapoints.find { it.id == dpId }.apply {
            if(this != null) {
                this.enabled = !this.enabled
            }
        }
    }

    override fun run() {
        while (enabled) {
            try {
                datapoints.forEach {
                    it.change()
                    println("${name}-${it.name}:${it.value}")
                }
                Thread.sleep(updatePeriod * updatePeriodType * 1000L)
            } catch (e:Exception) {
                Thread.currentThread().interrupt()
                e.printStackTrace()
            }
        }
    }
}