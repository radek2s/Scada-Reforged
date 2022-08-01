package org.reggster.srfcore

import com.rabbitmq.client.Channel
import org.reggster.srfcore.domain.data.InfluxDbService
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

@EnableRabbit
@Service
class MessageReceiver(
    val influxDbService: InfluxDbService
) {

    @RabbitListener(queues = ["datasource.values"], messageConverter = "jsonConventer")
    fun onNewDataSourceValue(id: Int, channel:Channel, @Header(AmqpHeaders.DELIVERY_TAG) tag: Long) =
        try {
            channel.basicAck(tag, false)
            influxDbService.getPoint()
        } catch (e :Exception) {
            channel.basicReject(tag, false)
            e.printStackTrace()
        }
}
//https://spring.io/guides/gs/messaging-rabbitmq/