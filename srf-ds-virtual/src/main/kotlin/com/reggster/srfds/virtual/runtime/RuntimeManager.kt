package com.reggster.srfds.virtual.runtime

import com.reggster.srfds.virtual.RabbitMessagePublisher
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import kotlin.concurrent.thread

@Component
class RuntimeManager(
    private val runtimeService: RuntimeService,
    private val messagePublisher: RabbitMessagePublisher
): ApplicationListener<ContextRefreshedEvent> {

    var initialized: Boolean = false

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if(initialized) return

        thread {
            while (true) {
                println("Polling datasources")
                runtimeService.dataSources.forEach {
                    println("Sending messages")
                    it.datapoints.forEach { dp ->
                        messagePublisher.publishEvent("scada_test", "values", object {
                            var dsId = it.id
                            var dpId = dp.id
                            var name = dp.name
                            var value = dp.value
                        })
                    }
                }
                Thread.sleep(5000)
            }
        }



    }

}