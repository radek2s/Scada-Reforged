package org.reggster.srfcore.domain.datasources

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/ds")
class DataSourcesController(
    private var datasourceService: ScadaDataSourceServiceImpl
) {

    @GetMapping(value = [""])
    @PreAuthorize("hasAuthority('ROLE_USER')")
    fun getAll(): ResponseEntity<List<ScadaDataSource>> =
        datasourceService.findAll().let {
            ResponseEntity.ok(it)
        }

    @GetMapping(value = ["/{id}"])
    @PreAuthorize("hasAuthority('ROLE_USER')")
    fun findById(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<ScadaDataSource> =
        datasourceService.findById(id, DataSourceType.valueOf(type))
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping(value = [""])
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun save(@RequestBody datasource: ScadaDataSource): ResponseEntity<String> =
        datasourceService.save(datasource).let {
            ResponseEntity.ok("Saved")
        }

}