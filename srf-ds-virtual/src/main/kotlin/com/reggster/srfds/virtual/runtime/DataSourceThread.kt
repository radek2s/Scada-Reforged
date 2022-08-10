package com.reggster.srfds.virtual.runtime

import com.reggster.srfds.virtual.RabbitMessagePublisher
import com.reggster.srfds.virtual.model.DataPointVirtualRT
import com.reggster.srfds.virtual.model.DataSourceVirtualRT

class DataSourceThread(
    val dataSource: DataSourceVirtualRT,
    messagePublisher: RabbitMessagePublisher
): Thread(dataSource.apply { setRabbit(messagePublisher) }) {

    val id = dataSource.id

    override fun start() {
        dataSource.enabled = true
        println("Starting DS: ${id}}")
        super.start()
    }

    override fun interrupt() {
        dataSource.enabled = false
        super.interrupt()
    }

//    fun addDataPoint(dp: DataPointVirtualRT) {
//        dataSource.addDataPoint(dp)
//    }
//
//    fun addDataPoint(dp: DataPointVirtualRT) {
//        dataSource.addDataPoint(dp)
//    }
//
//
//    fun setDataPointEnabled(dpId: Int, enabled: Boolean) {
//        dataSource.setDataPointEnabled(dpId, enabled)
//    }

//    fun setDataSourceState(enabled: Boolean) {
//        dataSource.enabled = enabled
//    }

    override fun run() {
        while (!this.isInterrupted) {
            try {
                super.run()
                sleep((dataSource.updatePeriod * dataSource.updatePeriodType * 1_000).toLong())
            } catch (e: Exception) {
                println("Stopped DS: $id")
                interrupt()
            }
        }
    }
}