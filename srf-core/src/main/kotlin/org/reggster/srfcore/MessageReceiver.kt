package org.reggster.srfcore

import com.rabbitmq.client.Channel
import org.reggster.srfcommons.async.PointValue
import org.reggster.srfcore.domain.data.InfluxDbService
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.simp.SimpMessagingTemplate
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.CrossOrigin
import java.time.Instant

@EnableRabbit
@Service
class MessageReceiver(
    val influxDbService: InfluxDbService,
    val wsTemplate: SimpMessagingTemplate
) {

    @RabbitListener(queues = ["datasource.values"], messageConverter = "jsonConventer")
    fun onNewDataSourceValue(value: PointValue, channel:Channel, @Header(AmqpHeaders.DELIVERY_TAG) tag: Long) =
        try {
            channel.basicAck(tag, false)
            println("Received from: ${value.dpId}")
            influxDbService.savePointValue(value.dsType, value.dsId, value.dpId, value.value, Instant.ofEpochMilli(value.time!!))
            wsTemplate.convertAndSend("/topic/values/${value.dsType}/${value.dsId}/${value.dpId}", value.value)
        } catch (e :Exception) {
            channel.basicReject(tag, false)
            e.printStackTrace()
        }
}
//https://spring.io/guides/gs/messaging-rabbitmq/