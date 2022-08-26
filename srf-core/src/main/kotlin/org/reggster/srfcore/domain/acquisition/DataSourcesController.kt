package org.reggster.srfcore.domain.acquisition

import io.swagger.v3.oas.annotations.Operation
import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcore.domain.acquisition.virtual.DataPointVirtualEntity
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/ds")
class DataSourcesController(
    private var datasourceService: ScadaDataSourceEntityServiceImpl
) {

    @Operation(summary = "Find all DataSources", tags = ["datasource"])
    @GetMapping(value = [""])
    fun getAll(): ResponseEntity<List<ScadaDataSourceEntity<ScadaDataPointEntity>>> =
        datasourceService.findAll().let {
            ResponseEntity.ok(it)
        }

    @Operation(summary = "Find DataSource", tags = ["datasource"])
    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<ScadaDataSourceEntity<ScadaDataPointEntity>>? =
        datasourceService.findById(id, ScadaDataSourceType.valueOf(type))
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())



    //TODO: Consider how to implement this communication in a clear way
    // Now DS has to be enabled: localhost:8081/api/v1/runner/8/enable

    @Operation(summary = "Update DataSource", tags = ["datasource"])
    @PutMapping(value = ["/{id}"])
    fun update(@RequestBody datasource: ScadaDataSourceEntity<ScadaDataPointEntity>, principal: Principal): ResponseEntity<ScadaDataSourceEntity<ScadaDataPointEntity>> =
        datasourceService.save(datasource, principal).let {
            ResponseEntity.ok(it)
        }

    @Operation(summary = "Create DataSource", tags = ["datasource"])
    @PostMapping(value = [""])
    fun create(@RequestBody datasource: ScadaDataSourceEntity<ScadaDataPointEntity>, principal: Principal): ResponseEntity<ScadaDataSourceEntity<ScadaDataPointEntity>> =
        datasourceService.create(datasource, principal).let {
            ResponseEntity.ok(it)
        }

    @Operation(summary = "Delete DataSource", tags = ["datasource"])
    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<String> =
        datasourceService.delete(id, ScadaDataSourceType.valueOf(type)).let {
            ResponseEntity.ok("Deleted")
        }

    @Operation(summary = "Add Data Point to DataSource", tags = ["datapoint"])
    @PostMapping(value = ["/{id}"])
    fun addDataPoint(@PathVariable id: Int, @RequestParam(value = "t") type: String, @RequestBody datapoint: DataPointVirtualEntity, principal: Principal): ResponseEntity<ScadaDataSourceEntity<ScadaDataPointEntity>> =
        datasourceService.createDataPoint(id, ScadaDataSourceType.valueOf(type), datapoint, principal).let {
            ResponseEntity.ok(it)
        }

    @Operation(summary = "Delete Data Point from DataSource", tags = ["datapoint"])
    @DeleteMapping(value = ["/{dsId}/{dpId}"])
    fun removeDataPoint(@PathVariable dsId: Int, @PathVariable dpId: Int, @RequestParam(value = "t") type: String, principal: Principal): ResponseEntity<String> =
        datasourceService.removeDataPoint(dsId, ScadaDataSourceType.valueOf(type), dpId, principal).let {
            ResponseEntity.ok("Deleted")
        }

}