package org.reggster.srfcore.domain.data

import org.reggster.srfcommons.async.PointValue
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant



@RestController
@RequestMapping("/api/v1/pv")
class PointValueController(
    private var influxDbService: InfluxDbService
) {

    @GetMapping(value = [""])
    fun getValues(@RequestParam dsId: Int, @RequestParam dpId: Int): ResponseEntity<List<PointValue>> =
        ResponseEntity.ok(influxDbService.getPointValues(dsId, dpId))

}