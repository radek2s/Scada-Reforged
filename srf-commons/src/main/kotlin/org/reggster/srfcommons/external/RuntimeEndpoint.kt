package org.reggster.srfcommons.external

import org.reggster.srfcommons.acquisition.ScadaDataPoint
import org.reggster.srfcommons.acquisition.ScadaDataSource
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RequestMapping(path = ["/api/v1/runner"])
interface RuntimeEndpoint<T: ScadaDataSource, D: ScadaDataPoint> {

    @PostMapping(value = [""])
    fun createRuntimeDataSource(@RequestBody dataSource: T): ResponseEntity<ExternalServiceResponse>

    @DeleteMapping(value = ["/{id}"])
    fun removeRuntimeDataSource(@PathVariable id: Int): ResponseEntity<ExternalServiceResponse>

    @GetMapping(value = ["/{dsId}/{dpId}"])
    fun setStateRuntimeDataPoint(@PathVariable dsId: Int, @PathVariable dpId: Int, @RequestParam enabled: Boolean): ResponseEntity<ExternalServiceResponse>

    @PostMapping(value = ["/{id}"])
    fun addRuntimeDataPoint(@PathVariable id: Int, @RequestBody datapoint: D): ResponseEntity<ExternalServiceResponse>

    @DeleteMapping(value = ["/{dsId}/{dpId}"])
    fun deleteRuntimeDataPoint(@PathVariable dsId: Int, @PathVariable dpId: Int): ResponseEntity<ExternalServiceResponse>

}