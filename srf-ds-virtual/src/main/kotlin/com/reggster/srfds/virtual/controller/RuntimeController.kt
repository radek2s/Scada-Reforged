package com.reggster.srfds.virtual.controller

import com.reggster.srfds.virtual.model.DataPointVirtualRT
import com.reggster.srfds.virtual.model.DataSourceVirtualRT
import com.reggster.srfds.virtual.runtime.RuntimeService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/api/v1/runner"])
class RuntimeController(
    private val runtimeService: RuntimeService
) {

    @PostMapping(value = [""])
    fun createRT(@RequestBody ds: DataSourceVirtualRT): ResponseEntity<String> =
        runtimeService.addDataSource(ds).let { ResponseEntity.ok("{\"status\": \"Added\" }") }

    @DeleteMapping(value = ["/{id}"])
    fun removeRT(@PathVariable id: Int): ResponseEntity<String> =
        runtimeService.removeDataSource(id).let { ResponseEntity.ok("{\"status\": \"Stopped\" }") }

    @GetMapping(value = ["/{dsId}/{dpId}"])
    fun enableDpRt(@PathVariable dsId: Int, @PathVariable dpId: Int, @RequestParam enabled: Boolean): ResponseEntity<String> =
        runtimeService.setDataPointEnabled(dsId, dpId, enabled).let { ResponseEntity.ok("{\"status\": \"Toggled point\" }") }

    @DeleteMapping(value = ["/{dsId}/{dpId}"])
    fun removeDpRt(@PathVariable dsId: Int, @PathVariable dpId: Int): ResponseEntity<String> =
        runtimeService.removeDataPoint(dsId, dpId).let { ResponseEntity.ok("{\"status\": \"Removed point\" }") }

    @PostMapping(value = ["/{id}"])
    fun addDpRt(@PathVariable id: Int, @RequestBody dp: DataPointVirtualRT): ResponseEntity<String> =
        runtimeService.addDataPoint(id, dp).let { ResponseEntity.ok("{\"status\": \"Added point\" }") }


}