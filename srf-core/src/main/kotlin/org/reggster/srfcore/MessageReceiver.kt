package org.reggster.srfcore

import com.rabbitmq.client.Channel
import org.reggster.srfcommons.async.PointValue
import org.reggster.srfcore.domain.data.InfluxDbService
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service
import java.time.Instant

@EnableRabbit
@Service
class MessageReceiver(
    val influxDbService: InfluxDbService
) {

    @RabbitListener(queues = ["datasource.values"], messageConverter = "jsonConventer")
    fun onNewDataSourceValue(value: PointValue, channel:Channel, @Header(AmqpHeaders.DELIVERY_TAG) tag: Long) =
        try {
            channel.basicAck(tag, false)
            println("Received from: ${value.dpId}")
            influxDbService.savePointValue(value.dsId, value.dpId, value.value, Instant.ofEpochMilli(value.time!!))

            //TODO: Save that value to influxDB
        } catch (e :Exception) {
            channel.basicReject(tag, false)
            e.printStackTrace()
        }
}
//https://spring.io/guides/gs/messaging-rabbitmq/