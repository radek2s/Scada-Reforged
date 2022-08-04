package org.reggster.srfcore

import org.reggster.srfcore.domain.data.InfluxDbService
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
    private val passwordEncoder: PasswordEncoder,
    private val influxService: InfluxDbService
) : ApplicationListener<ContextRefreshedEvent> {

    var initialized: Boolean = false

    @Transactional
    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        if(initialized) return
        createRoles()

        val adminUser = ScadaUser(
            1, "admin", passwordEncoder.encode("admin"), "Bilbo"
        )
        adminUser.roles = setOf(roleRepository.findByRoleName("ROLE_ADMIN").get())
        userRepository.save(adminUser)

        val standardUser = ScadaUser(
            2, "user", passwordEncoder.encode("user"), "Sam"
        )
        standardUser.roles = setOf(roleRepository.findByRoleName("ROLE_USER").get())
        userRepository.save(standardUser)

        val mgmtUser = ScadaUser(
            3, "mgmt", passwordEncoder.encode("mgmt"), "Manager"
        )
        mgmtUser.roles = setOf(roleRepository.findByRoleName("ROLE_MGMT").get())
        userRepository.save(mgmtUser)
    }

    private fun createRoles() {
        createRole(1, "ROLE_ADMIN", "Execute all")
        createRole(2, "ROLE_MGMT", "DataSource manager")
        createRole(3, "ROLE_USER", "Standard execution")
        createRole(4, "ROLE_GUEST", "Anonymous access")
    }

    @Transactional
    fun createRole(id: Int, name: String, description: String?): Role =
        roleRepository.findByRoleName(name).orElse(
            Role(id, name, description).also {
                roleRepository.save(it)
            }
        )

//https://github.com/influxdata/influxdb-client-java/blob/master/examples/src/main/java/example/KotlinWriteApi.kt
    //Influx point entity to store
    //Bucket creation
    //https://github.com/influxdata/influxdb-client-java#use-management-api-to-create-a-new-bucket-in-influxdb-2x

}