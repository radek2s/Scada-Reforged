package org.reggster.srfcore.security.acl

import org.springframework.data.repository.CrudRepository
import java.util.*

interface ScadaUserRepo : CrudRepository<ScadaUser, Long> {
    override fun findAll(): List<ScadaUser>

    fun findByUsername(username: String): Optional<ScadaUser>
}