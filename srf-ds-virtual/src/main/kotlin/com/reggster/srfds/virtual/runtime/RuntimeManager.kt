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
                    messagePublisher.publishEvent("scada_test","values", it.value.toString())
                }
                Thread.sleep(5000)
            }
        }



    }

}