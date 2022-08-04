package org.reggster.srfcore

import com.rabbitmq.client.Channel
import org.reggster.srfcore.domain.data.InfluxDbService
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.amqp.support.AmqpHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.stereotype.Service

data class ValueObj(
    var dsId: Int,
    var dpId: Int,
    var name: String,
    var value: Int
)
@EnableRabbit
@Service
class MessageReceiver(
    val influxDbService: InfluxDbService
) {

    @RabbitListener(queues = ["datasource.values"], messageConverter = "jsonConventer")
    fun onNewDataSourceValue(value: ValueObj, channel:Channel, @Header(AmqpHeaders.DELIVERY_TAG) tag: Long) =
        try {
            channel.basicAck(tag, false)
            println("Received from: ${value.name}")
            influxDbService.savePointValue(value.dsId, value.dpId, value.value)
            //TODO: Save that value to influxDB
        } catch (e :Exception) {
            channel.basicReject(tag, false)
            e.printStackTrace()
        }
}
//https://spring.io/guides/gs/messaging-rabbitmq/