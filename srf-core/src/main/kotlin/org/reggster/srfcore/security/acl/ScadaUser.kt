package org.reggster.srfcore.security.acl

import javax.persistence.*

@Entity
@Table(name = "scada_user")
class ScadaUser(
    @Id
    @GeneratedValue
    val id: Int? = null,

    @Column(unique = true)
    var username: String? = null,

    @Column()
    var password: String? = null,

    @Column(name = "first_name")
    var firstName: String? = null,

    @ManyToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "user_role",
        joinColumns = [JoinColumn(name = "user_id", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "role_id", referencedColumnName = "id")]
    )
    var roles: Set<Role>? = null
)