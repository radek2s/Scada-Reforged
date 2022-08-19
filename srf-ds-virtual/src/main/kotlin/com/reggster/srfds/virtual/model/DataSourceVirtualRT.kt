package com.reggster.srfds.virtual.model

import com.reggster.srfds.virtual.RabbitMessagePublisher
import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.TimePeriod
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
    override var updatePeriodType: TimePeriod,
    var datapoints: MutableList<DataPointVirtualRT> = mutableListOf(),
    var value: Int
) : DataSourceVirtual, Runnable {
    private var messagePublisher: RabbitMessagePublisher? = null

    fun setRabbit(rabbit: RabbitMessagePublisher) {
        this.messagePublisher = rabbit
    }

    fun addDataPoint(dp: DataPointVirtualRT) {
        datapoints.add(dp)
    }

    fun setDataPointEnabled(dpId: Int, enabled: Boolean) {
        datapoints.find { it.id == dpId }?.enabled = enabled
    }

    fun removeDataPoint(id: Int) {
        val dp = datapoints.find { it.id == id } ?: return
        datapoints.remove(dp)
    }

    override fun run() {
        if(!enabled) { return }
        datapoints.forEach {
            if(it.enabled) {
                it.change()

                println("${name}-${it.name}:${it.value}")
                messagePublisher?.publishPointValue(PointValue(
                    ScadaDataSourceType.VIRTUAL, id, it.id, it.value, Instant.now().toEpochMilli()
                ))
            }
        }
    }
}