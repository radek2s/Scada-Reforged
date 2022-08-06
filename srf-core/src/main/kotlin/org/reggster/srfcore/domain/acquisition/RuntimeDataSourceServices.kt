package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

@Component
class RuntimeDataSourceServices {

    @Value("\${scada.service.virtual.url}")
    lateinit var virtualRuntimeServiceUrl: String

    @Autowired
    lateinit var restTemplate: RestTemplate

    fun initDataSource(ds: ScadaDataSourceEntity) =
        restTemplate.postForObject(
            UriComponentsBuilder.fromUriString("http:${virtualRuntimeServiceUrl}/api/v1/runner").build().encode().toUri(), ds as DataSourceVirtual, String.javaClass)
            .also { println("Sent") }

    fun enableDataSource(id: Int) =
        restTemplate.getForObject(
            UriComponentsBuilder.fromUriString("http:${virtualRuntimeServiceUrl}/api/v1/runner/${id}/enable").build().encode().toUri(),
            String.javaClass
        )
}