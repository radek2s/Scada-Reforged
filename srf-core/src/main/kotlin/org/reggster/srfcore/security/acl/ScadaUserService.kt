package org.reggster.srfcore.security.acl

import org.springframework.security.acls.domain.DefaultPermissionFactory
import org.springframework.security.acls.domain.GrantedAuthoritySid
import org.springframework.security.acls.domain.ObjectIdentityImpl
import org.springframework.security.acls.domain.PermissionFactory
import org.springframework.security.acls.domain.PrincipalSid
import org.springframework.security.acls.model.MutableAcl
import org.springframework.security.acls.model.MutableAclService
import org.springframework.security.acls.model.NotFoundException
import org.springframework.security.acls.model.ObjectIdentity
import org.springframework.security.acls.model.Permission
import org.springframework.security.acls.model.Sid
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service("userDetailsService")
class ScadaUserService(
    private var scadaUserRepo: ScadaUserRepo,
    private var aclService: MutableAclService,
) : UserDetailsService {

    private var permissionFactory: PermissionFactory = DefaultPermissionFactory()

    fun findAll(): Iterable<ScadaUser> = scadaUserRepo.findAll()

    @Transactional
    fun addPermission(sid: Sid, type: Class<*>, id: Long, permissions: List<Permission>) {
        permissions.forEach {
            val objectIdentity: ObjectIdentity = ObjectIdentityImpl(type, id)
            val acl: MutableAcl = try {
                aclService.readAclById(objectIdentity) as MutableAcl
            } catch (e: NotFoundException) {
                aclService.createAcl(objectIdentity)
            }

            acl.insertAce(acl.entries.size, it, sid, true)
            aclService.updateAcl(acl)
        }
    }

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails {
        val user = scadaUserRepo.findByUsername(username)
            .orElseThrow { UsernameNotFoundException("User: $username") }

        val authorities = ArrayList<GrantedAuthority>()
        user.roles!!.forEach { role -> authorities.add(SimpleGrantedAuthority(role.roleName)) }

        return User(
            user.username,
            user.password,
            authorities
        )
    }

}