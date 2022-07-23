package org.reggster.srfcore.domain.datasources

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/data")
class DataSourcesController(
    private var ds: ScadaDataSourceServiceImpl
) {

//    lateinit

    @GetMapping(value = [""])
    fun getAll(): ResponseEntity<List<ScadaDataSource>> =
        ds.findAll().let {
            ResponseEntity.ok(it)
        }

}