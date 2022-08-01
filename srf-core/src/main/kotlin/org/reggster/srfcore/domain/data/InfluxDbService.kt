package org.reggster.srfcore.domain.data

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class InfluxDbService {

    @Value("\${influxdb.url}")
    lateinit var url: String

    @Value("\${influxdb.token}")
    lateinit var token: String

    @Value("\${influxdb.organization}")
    lateinit var organization: String

    @Value("\${influxdb.bucket}")
    lateinit var bucket: String

    private lateinit var client: InfluxDBClient

    @PostConstruct
    fun init() {
        try {
            client = InfluxDBClientFactory.create(url, token.toCharArray(), organization, bucket)
            println("Established InfluxDB connection")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    fun getPoint() {
        try {
//            val client: InfluxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray(), organization, bucket)
            val querry: String = "from(bucket: \"${bucket}\") |> range(start: -10d) |> filter(fn: (r) => r._measurement == \"weatherstation\")"
            val res = client.queryApi.query(querry)
            res.forEach { iti ->
                iti.records.forEach {
                    println("${it.time} ${it.measurement}: ${it.field}=${it.value}")
                }
            }

        }catch (e: Exception) {
            e.printStackTrace()
        }



    }
}