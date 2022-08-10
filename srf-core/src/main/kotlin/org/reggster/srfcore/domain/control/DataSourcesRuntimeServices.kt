package org.reggster.srfcore.domain.control

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcommons.acquisition.virtual.DataPointVirtual
import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import org.reggster.srfcore.domain.acquisition.ScadaDataPointEntity
import org.reggster.srfcore.domain.acquisition.ScadaDataSourceEntity
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class DataSourcesRuntimeServices {

    @Value("\${scada.service.virtual.url}")
    lateinit var virtualRuntimeServiceUrl: String

    @Autowired
    lateinit var restTemplate: RestTemplate

    private fun getServiceEndpointUrl (type: ScadaDataSourceType) = when (type) {
        ScadaDataSourceType.VIRTUAL -> virtualRuntimeServiceUrl
    }

    fun enableDataSource(ds: ScadaDataSourceEntity<ScadaDataPointEntity>) =
        restTemplate.postForObject(
            UriComponentsBuilder.fromUriString("http:${getServiceEndpointUrl(ds.type)}/api/v1/runner")
                .build().encode().toUri(), ds as DataSourceVirtual, String.javaClass)
            .also { println("Sent") }

    fun disableDataSource(id: Int, type: ScadaDataSourceType) =
        restTemplate.delete(
            UriComponentsBuilder.fromUriString("http:${getServiceEndpointUrl(type)}/api/v1/runner/${id}").build().encode().toUri())

    fun setDataPointState(dsId: Int, type: ScadaDataSourceType, dpId: Int, enabled: Boolean) =
        restTemplate.getForObject(
            UriComponentsBuilder.fromUriString("http:${getServiceEndpointUrl(type)}/api/v1/runner/${dsId}/${dpId}?enabled=${enabled}").build().encode().toUri(), String.javaClass
        ).also { println("Toggled $enabled") }

    fun addDataPoint(dsId: Int, type: ScadaDataSourceType, datapoint: ScadaDataPointEntity) =
        restTemplate.postForObject(
            UriComponentsBuilder.fromUriString("http:${getServiceEndpointUrl(type)}/api/v1/runner/${dsId}")
                .build().encode().toUri(), datapoint as DataPointVirtual, String.javaClass)
            .also { println("Point added") }

    fun deleteDataPoint(dsId: Int, type: ScadaDataSourceType, dpId: Int) =
        restTemplate.delete(
            UriComponentsBuilder.fromUriString("http:${getServiceEndpointUrl(type)}/api/v1/runner/${dsId}/${dpId}").build().encode().toUri())




}