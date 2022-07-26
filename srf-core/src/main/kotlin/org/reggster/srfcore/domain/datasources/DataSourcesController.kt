package org.reggster.srfcore.domain.datasources

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/api/v1/ds")
class DataSourcesController(
    private var datasourceService: ScadaDataSourceServiceImpl
) {

    @GetMapping(value = [""])
    fun getAll(): ResponseEntity<List<ScadaDataSource>> =
        datasourceService.findAll().let {
            ResponseEntity.ok(it)
        }

    @GetMapping(value = ["/{id}"])
    fun findById(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<ScadaDataSource> =
        datasourceService.findById(id, DataSourceType.valueOf(type))
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PutMapping(value = ["/{id}"])
    fun update(@RequestBody datasource: ScadaDataSource, principal: Principal): ResponseEntity<ScadaDataSource> =
        datasourceService.save(datasource, principal).let {
            ResponseEntity.ok(it)
        }

    @PostMapping(value = [""])
    fun create(@RequestBody datasource: ScadaDataSource, principal: Principal): ResponseEntity<ScadaDataSource> =
        datasourceService.create(datasource, principal).let {
            ResponseEntity.ok(it)
        }

    @DeleteMapping(value = ["/{id}"])
    fun delete(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<String> =
        datasourceService.delete(id, DataSourceType.valueOf(type)).let {
            ResponseEntity.ok("Deleted")
        }

}