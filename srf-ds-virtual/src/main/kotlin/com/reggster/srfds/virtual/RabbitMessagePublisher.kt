package com.reggster.srfds.virtual

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RabbitMessagePublisher {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    fun publishEvent(exchange: String, routingKey: String, message: Any) {
        rabbitTemplate.convertAndSend(exchange,routingKey, message)
    }
}