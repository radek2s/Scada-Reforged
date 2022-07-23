package org.reggster.srfcore.domain.datasources.other

import org.reggster.srfcore.domain.datasources.virtual.DataSourceVirtual
import org.reggster.srfcore.domain.datasources.virtual.DataSourceVirtualService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/ds2")
class DataSourceOtherController(
    private val dsService: DataSourceOtherService
) {

    @GetMapping(value = ["/{id}"])
    @PreAuthorize("hasAuthority('ROLE_USER')")
    fun getOne(@PathVariable id: Int): ResponseEntity<DataSourceOther> =
        dsService.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping(value = [""])
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun save(@RequestBody datasource: DataSourceOther): ResponseEntity<String> =
        dsService.save(datasource).let {
            ResponseEntity.ok("Saved")
        }
}