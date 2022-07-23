package org.reggster.srfcore.domain.datasources.virtual

import java.util.Random

class DataSourceVirtualRT(
    private var value: Double = 0.0,
    private var stopped: Boolean = false,
    private val ds: DataSourceVirtual
): Thread() {

    fun stopDataSource() {
        synchronized(this) {
            this.stopped = true
        }
    }

    private fun getRandomValue() =
        Random().nextDouble()

    override fun run() {
        while (!stopped && ds.enabled) {
            println(getRandomValue())
            try {
                sleep(ds.updatePeriod * 1000L)
            } catch (e: InterruptedException) {
                println("Stopped")
            }
        }
    }
}