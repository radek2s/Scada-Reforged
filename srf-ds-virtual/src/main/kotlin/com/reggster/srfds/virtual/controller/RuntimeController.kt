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
    fun addDs(@RequestBody ds: DataSourceVirtualRT): ResponseEntity<String> =
        runtimeService.addDataSource(ds).let { ResponseEntity.ok("{\"status\": \"Added\" }") }

    @GetMapping(value = ["/{id}"])
    fun getValue(@PathVariable id: Int): ResponseEntity<Int> =
        runtimeService.getCurrentValueById(id).let { ResponseEntity.ok(it) }

    @GetMapping(value = ["/{id}/enable"])
    fun enable(@PathVariable id: Int): ResponseEntity<String> =
        runtimeService.enableById(id).let { ResponseEntity.ok("{\"status\": \"Enabled\" }") }

    @GetMapping(value = ["/{id}/disable"])
    fun disable(@PathVariable id: Int): ResponseEntity<String> =
        runtimeService.disableById(id).let { ResponseEntity.ok("Disabled") }

    @PostMapping(value = ["/{id}"])
    fun addDp(@PathVariable id: Int, @RequestBody dp: DataPointVirtualRT): ResponseEntity<String> =
        runtimeService.addDataPoint(id, dp).let { ResponseEntity.ok("Added") }

    @GetMapping(value = ["/{dsId}/{dpId}/toggle"])
    fun toggleDp(@PathVariable dsId: Int, @PathVariable dpId: Int): ResponseEntity<String> =
        runtimeService.toggleDataPoint(dsId, dpId).let { ResponseEntity.ok("Toggled") }


}