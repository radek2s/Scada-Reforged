package com.reggster.srfds.virtual.model

import org.reggster.srfcommons.acquisition.DataPointType
import org.reggster.srfcommons.acquisition.virtual.DataPointVirtual
import kotlin.random.Random

class DataPointVirtualRT(
    override var id: Int,
    override var sid: String,
    override var name: String,
    override var dataType: DataPointType,
    override var settable: Boolean,
    override var enabled: Boolean,
    override var changeType: Int,
    override var minValue: Int,
    override var maxValue: Int
) : DataPointVirtual {
    var value: Double = 0.0

    fun change() {
        if(!enabled) return
        value = when(dataType) {
            DataPointType.BINARY -> when(Random.nextBoolean()) { true -> 1.0; false -> 0.0}
            DataPointType.INTEGER -> Random.Default.nextInt(minValue, maxValue).toDouble()
            DataPointType.DOUBLE -> Random.Default.nextDouble(minValue.toDouble(), maxValue.toDouble())
        }
    }
}