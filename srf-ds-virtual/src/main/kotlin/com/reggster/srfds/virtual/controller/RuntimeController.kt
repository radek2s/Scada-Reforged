package com.reggster.srfds.virtual.controller

import com.reggster.srfds.virtual.model.DataPointVirtualRT
import com.reggster.srfds.virtual.model.DataSourceVirtualRT
import com.reggster.srfds.virtual.runtime.RuntimeService
import org.reggster.srfcommons.external.ExternalServiceResponse
import org.reggster.srfcommons.external.RuntimeEndpoint
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v1/runner"])
class RuntimeController(
    private val runtimeService: RuntimeService
): RuntimeEndpoint<DataSourceVirtualRT, DataPointVirtualRT> {

    override fun createRuntimeDataSource(dataSource: DataSourceVirtualRT): ResponseEntity<ExternalServiceResponse> =
        runtimeService.addDataSource(dataSource).let { ResponseEntity.ok(ExternalServiceResponse(200, "Created", null)) }

    override fun removeRuntimeDataSource(id: Int): ResponseEntity<ExternalServiceResponse> =
        runtimeService.removeDataSource(id).let { ResponseEntity.ok(ExternalServiceResponse(200, "Stopped", null)) }

    override fun setStateRuntimeDataPoint(dsId: Int, dpId: Int, enabled: Boolean): ResponseEntity<ExternalServiceResponse> =
        runtimeService.setDataPointEnabled(dsId, dpId, enabled).let { ResponseEntity.ok(ExternalServiceResponse(200, "DataPoint state", enabled)) }

    override fun deleteRuntimeDataPoint(dsId: Int, dpId: Int): ResponseEntity<ExternalServiceResponse> =
        runtimeService.removeDataPoint(dsId, dpId).let { ResponseEntity.ok(ExternalServiceResponse(200, "DataPoint Removed", null)) }

    override fun addRuntimeDataPoint(id: Int, datapoint: DataPointVirtualRT): ResponseEntity<ExternalServiceResponse> =
        runtimeService.addDataPoint(id, datapoint).let { ResponseEntity.ok(ExternalServiceResponse(200, "DataPoint Added", null)) }

}