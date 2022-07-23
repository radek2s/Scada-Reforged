package org.reggster.srfcore.security.acl

import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface RoleRepository : JpaRepository<Role, Long> {

    fun findByRoleName(name: String): Optional<Role>

}