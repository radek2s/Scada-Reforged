package com.reggster.srfds.virtual.model

import org.reggster.srfcommons.acquisition.DataPointType
import org.reggster.srfcommons.acquisition.virtual.DataPointVirtual
import kotlin.random.Random
import kotlin.random.nextInt

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
    var value: Int = 0

    fun change() {
        if(!enabled) return
        value = when(dataType) {
            DataPointType.BINARY -> Random.Default.nextInt(0,1)
            DataPointType.DOUBLE -> Random.Default.nextInt(minValue, maxValue)
            DataPointType.INTEGER -> Random.Default.nextInt(minValue, maxValue)
        }
    }
}