package com.reggster.srfds.virtual.model

import com.reggster.srfds.virtual.RabbitMessagePublisher
import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import org.reggster.srfcommons.async.PointValue
import java.time.Instant

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
    private var messagePublisher: RabbitMessagePublisher? = null


    fun setRabbit(rabbit: RabbitMessagePublisher) {
        this.messagePublisher = rabbit
    }

    fun interrupt() {
        enabled = false
    }

    fun start(messagePublisher: RabbitMessagePublisher) {
        this.messagePublisher = messagePublisher
//        if(worker == null) {
//            worker = Thread(this)
//            worker.sta
//            worker?.start()
//        }

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

//    fun invokeUpdate() {
//        this.worker?.
//
//    }

    override fun run() {
        while (enabled) {
            try {
                datapoints.forEach {

                    println("Before: ${it.name} - ${Instant.now()}")
                    if(!it.enabled) return
                    println("After: ${it.name}")
                    it.change()

                    println("${name}-${it.name}:${it.value}")
                    messagePublisher?.publishPointValue(PointValue(
                        id, it.id, it.value, Instant.now().toEpochMilli()
                    ))
                }
//                Thread.currentThread().sleep(1000)
                Thread.sleep((updatePeriod * updatePeriodType * 1_000).toLong())
            } catch (e:Exception) {
                Thread.currentThread().interrupt()
                e.printStackTrace()
            }
        }
    }
}