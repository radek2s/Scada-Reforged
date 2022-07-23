package org.reggster.srfcore.security.acl

import javax.persistence.*

@Entity
@Table(name = "scada_role")
data class Role(

    @Id
    @GeneratedValue
    val id: Int = 0,

    @Column(name = "role_name", updatable = false)
    var roleName: String = "",

    @Column(name = "description", updatable = false)
    var description: String? = "",
)