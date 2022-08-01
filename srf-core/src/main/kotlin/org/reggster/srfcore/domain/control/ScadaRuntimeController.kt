package org.reggster.srfcore.domain.control

import org.springframework.context.ApplicationContext
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/api/v1/runner"])
class ScadaRuntimeController(
    private val context: ApplicationContext
) {

    @GetMapping(value = ["/enable"])
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun enableDS(): ResponseEntity<String> =
        (context.getBean("getScadaRuntime") as ScadaRuntime).let {
//            it.initDataSources()
//            it.startDataSources()
            ResponseEntity.ok("Started")
        }

    @GetMapping(value = ["/disable"])
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    fun disableDS(): ResponseEntity<String> =
        (context.getBean("getScadaRuntime") as ScadaRuntime).let {
//            it.stopDataSources()
            ResponseEntity.ok("Stopped")
        }
}