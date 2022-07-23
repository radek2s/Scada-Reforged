package org.reggster.srfcore.domain.datasources.virtual

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/ds")
class DataSourceVirtualController(
    private val dsService: DataSourceVirtualService
) {

    @GetMapping(value = ["/{id}"])
    @PreAuthorize("hasAuthority('ROLE_USER')")
    fun getOne(@PathVariable id: Int): ResponseEntity<DataSourceVirtual> =
        dsService.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping(value = [""])
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun save(@RequestBody datasource: DataSourceVirtual): ResponseEntity<String> =
        dsService.save(datasource).let {
            ResponseEntity.ok("Saved")
        }
}