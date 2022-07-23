package org.reggster.srfcore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ScadaReforgedApplication

//https://github.com/JahnelGroup/spring-boot-samples/tree/master/spring-boot-acl/src/main/kotlin/com/jahnelgroup/acl/config
fun main(args: Array<String>) {
	runApplication<ScadaReforgedApplication>(*args)
}
