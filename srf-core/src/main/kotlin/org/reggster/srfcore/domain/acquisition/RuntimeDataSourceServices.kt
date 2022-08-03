package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.virtual.DataSourceVirtual
import org.reggster.srfcore.domain.acquisition.virtual.DataSourceVirtualEntity
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

    fun initDataSource(ds: DataSourceVirtualEntity) =
        restTemplate.postForObject(
            UriComponentsBuilder.fromUriString("http:${virtualRuntimeServiceUrl}/api/v1/runner").build().encode().toUri(), ds, DataSourceVirtual::class.java)
            .also { println("Sent") }

//    fun addAll()
}