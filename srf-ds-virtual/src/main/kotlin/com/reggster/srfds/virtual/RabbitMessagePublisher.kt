package com.reggster.srfds.virtual

import org.reggster.srfcommons.async.PointValue
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RabbitMessagePublisher {

    @Autowired
    lateinit var rabbitTemplate: RabbitTemplate

    //Publish value
    fun publishPointValue(pv: PointValue)
        = rabbitTemplate.convertAndSend("scada_test", "values", pv)

//    fun

    // DataSources (toggle:enable-disable)


}