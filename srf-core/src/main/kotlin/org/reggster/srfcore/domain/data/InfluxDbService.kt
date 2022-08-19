package org.reggster.srfcore.domain.data

import com.influxdb.client.InfluxDBClient
import com.influxdb.client.InfluxDBClientFactory
import com.influxdb.client.WriteApiBlocking
import com.influxdb.client.domain.WritePrecision
import com.influxdb.client.write.Point
import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.async.PointValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
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


    fun getPointValues(dsId: Int, dpId: Int, dsType: ScadaDataSourceType): List<PointValue> {
        try {
//            val client: InfluxDBClient = InfluxDBClientFactory.create("http://localhost:8086", token.toCharArray(), organization, bucket)
            val querry: String = "from(bucket: \"${bucket}\") |> range(start: -1d) |> filter(fn: (r) => r._measurement == \"point_values\" and r.datasource == \"${dsId}\" and r.datapoint == \"${dpId}\" and r.type == \"${dsType.name}\")"
            println(querry)
            val results: MutableList<PointValue> = mutableListOf()
            val res = client.queryApi.query(querry)
            res.forEach { iti ->

                iti.records.forEach {

                    println("${it.time} ${it.measurement}: ${it.field}=${it.value} DS:${it.getValueByKey("datasource")}")
                    results.add(PointValue(
                        dsType,
                        dsId,
                        dpId,
                        it.value as Double,
                        it.time?.toEpochMilli()
                    ))
                }
            }
            return results.toList()
        } catch (e: Exception) {
            e.printStackTrace()
            return listOf()
        }
    }

    fun savePointValue(type: ScadaDataSourceType, dsId: Int, dpId: Int, value: Double, time: Instant?) {
        try {
            val t: Instant = time ?: Instant.now()
            val point: Point = Point.measurement("point_values")
                .addTag("datasource", "$dsId")
                .addTag("datapoint", "$dpId")
                .addTag("type", type.name)
                .addField("value", value)
                .time(t.toEpochMilli(), WritePrecision.MS)

            val writeApi: WriteApiBlocking = client.writeApiBlocking

            writeApi.writePoint(point)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}