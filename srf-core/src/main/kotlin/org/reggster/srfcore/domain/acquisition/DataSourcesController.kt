package org.reggster.srfcore.domain.acquisition

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api/v1/ds")
class DataSourcesController(
    private var datasourceService: ScadaDataSourceEntityServiceImpl
) {

    @GetMapping(value = [""])
    fun getAll(): ResponseEntity<List<ScadaDataSourceEntity>> =
        datasourceService.findAll().let {
            ResponseEntity.ok(it)
        }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<ScadaDataSourceEntity>? =
        datasourceService.findById(id, ScadaDataSourceType.valueOf(type))
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PutMapping(value = ["/{id}"])
    fun update(@RequestBody datasource: ScadaDataSourceEntity, principal: Principal): ResponseEntity<ScadaDataSourceEntity> =
        datasourceService.save(datasource, principal).let {
            ResponseEntity.ok(it)
        }

    @PostMapping(value = [""])
    fun create(@RequestBody datasource: ScadaDataSourceEntity, principal: Principal): ResponseEntity<ScadaDataSourceEntity> =
        datasourceService.create(datasource, principal).let {
            ResponseEntity.ok(it)
        }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<String> =
        datasourceService.delete(id, ScadaDataSourceType.valueOf(type)).let {
            ResponseEntity.ok("Deleted")
        }

}