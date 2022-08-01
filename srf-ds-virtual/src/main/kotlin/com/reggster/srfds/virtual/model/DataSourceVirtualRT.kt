package com.reggster.srfds.virtual.model

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import java.util.*

class DataSourceVirtualRT(
    override val type: ScadaDataSourceType,
    override var id: Int,
    override var sid: String,
    override var name: String,
    override var enabled: Boolean,
    override var updatePeriod: Int,
    override var updatePeriodType: Int,
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

    override fun run() {
        while (enabled) {
            try {
                value = Random().nextInt(0, 100)
                println("${name}:${value}")
                Thread.sleep(updatePeriod * updatePeriodType * 1000L)
            } catch (e:Exception) {
                Thread.currentThread().interrupt()
                e.printStackTrace()
            }
        }
    }
}