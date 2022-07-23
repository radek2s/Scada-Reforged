package org.reggster.srfcore

import org.reggster.srfcore.security.acl.ScadaUser
import org.reggster.srfcore.security.acl.ScadaUserRepo
import org.reggster.srfcore.security.acl.Role
import org.reggster.srfcore.security.acl.RoleRepository
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class ScadaReforgedInit(
    private val userRepository: ScadaUserRepo,
    private val roleRepository: RoleRepository,
    private val passwordEncoder: PasswordEncoder
) : ApplicationListener<ContextRefreshedEvent> {

    var initialized: Boolean = false

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if(initialized) return
        createRoles()

        val adminUser = ScadaUser(
            1, "admin", passwordEncoder.encode("admin"), "Bilbo"
        )
        adminUser.roles = setOf(
            roleRepository.findByRoleName("ROLE_ADMIN").get(),
            roleRepository.findByRoleName("ROLE_USER").get(),
            roleRepository.findByRoleName("ROLE_GUEST").get())
        userRepository.save(adminUser)

        val standardUser = ScadaUser(
            2, "user", passwordEncoder.encode("user"), "Sam"
        )
        standardUser.roles = setOf(roleRepository.findByRoleName("ROLE_USER").get())
        userRepository.save(standardUser)

    }

    private fun createRoles() {
        createRole(1, "ROLE_ADMIN", "Execute all")
        createRole(2, "ROLE_USER", "Standard execution")
        createRole(3, "ROLE_GUEST", "Anonymous access")
    }

    @Transactional
    fun createRole(id: Int, name: String, description: String?): Role =
        roleRepository.findByRoleName(name).orElse(
            Role(id, name, description).also {
                roleRepository.save(it)
            }
        )



}