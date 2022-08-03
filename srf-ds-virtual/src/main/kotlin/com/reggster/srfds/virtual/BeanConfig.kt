package com.reggster.srfds.virtual

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableScheduling
class BeanConfig {

    @Bean
    fun jsonConventer(): MessageConverter =
        Jackson2JsonMessageConverter(ObjectMapper().registerModule(KotlinModule()))

    @Bean
    fun rabbitTemplate(connectionFactory: ConnectionFactory) =
        RabbitTemplate(connectionFactory).apply {
            messageConverter = jsonConventer()
        }

}