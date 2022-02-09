package org.reggsoft.srfcore.services;

import org.reggsoft.srfcore.persistance.dao.AclSidRepository;
import org.reggsoft.srfcore.persistance.dao.RoleRepository;
import org.reggsoft.srfcore.persistance.dao.ScadaUserRepository;
import org.reggsoft.srfcore.persistance.entity.Privilege;
import org.reggsoft.srfcore.persistance.entity.Role;
import org.reggsoft.srfcore.persistance.entity.ScadaUser;
import org.reggsoft.srfcore.persistance.entity.AclSid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class ScadaUserService implements UserDetailsService {

    @Autowired
    ScadaUserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

//    @Autowired
//    AclSidRepository aclSidRepository;

    @Autowired
    PermissionService permissionService;

    @Override
    @Transactional
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        ScadaUser user = userRepository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException(username);
        }
//        return user;
//        if (user == null) {
//            return new org.springframework.security.core.userdetails.User(
//                    " ", " ", true, true, true, true,
//                    getAuthorities(Arrays.asList(
//                            roleRepository.findByName("ROLE_USER"))));
//        }
        return new User(
                user.getUsername(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
//        return user;

//        return new org.springframework.security.core.userdetails.User(
//                user.getEmail(), user.getPassword(), user.isEnabled(), true, true,
//                true, getAuthorities(user.getRoles()));
    }

    public UserDetails saveUser(ScadaUser user) {
        //When new user is saved we shoud add a new entry to "ACL_SID" table
        //Principal = 1 and SID = username
//        AclSid sid = new AclSid();
//        sid.setPrincipal(1);
//        sid.setSid(user.getUsername());
//        aclSidRepository.save(sid);
//        permissionService.addPermission(user.getUsername(), );
//        Collection<Role> test = aclSidRepository.findAllRoles();
//        for (Role r: test) {
//            System.out.println(r.getName());
//        }
        return userRepository.save(user);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {

        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {

        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();
        for (Role role : roles) {
            privileges.add(role.getName());
            collection.addAll(role.getPrivileges());
        }
        for (Privilege item : collection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
