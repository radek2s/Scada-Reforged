package org.reggster.srfcore.domain.control

import org.reggster.srfcommons.acquisition.ScadaDataSourceType
import org.reggster.srfcore.domain.acquisition.ScadaDataSourceEntityServiceImpl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/rt")
class DataSourcesRuntimeController(
    private var datasourceService: ScadaDataSourceEntityServiceImpl
) {

    @GetMapping(value = ["/{id}/enable"])
    fun initById(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<String>? =
        datasourceService.enable(id, ScadaDataSourceType.valueOf(type))
            .let { ResponseEntity.ok("OK") }

    @GetMapping(value = ["/{id}/disable"])
    fun initEById(@PathVariable id: Int, @RequestParam(value = "t") type: String): ResponseEntity<String>? =
        datasourceService.disable(id, ScadaDataSourceType.valueOf(type))
            .let { ResponseEntity.ok("OK") }

    @GetMapping(value = ["/{dsId}/{dpId}"])
    fun setDPState(@PathVariable dsId: Int, @PathVariable dpId: Int, @RequestParam(value = "t") type: String, @RequestParam enabled: Boolean): ResponseEntity<String>? =
        datasourceService.setDataPointState(dsId, ScadaDataSourceType.valueOf(type), dpId, enabled)
            .let { ResponseEntity.ok("OK") }


}