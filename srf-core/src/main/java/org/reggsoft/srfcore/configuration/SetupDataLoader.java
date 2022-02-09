package org.reggsoft.srfcore.configuration;

import org.reggsoft.srfcore.persistance.dao.PrivilegeRepository;
import org.reggsoft.srfcore.persistance.dao.RoleRepository;
import org.reggsoft.srfcore.persistance.dao.ScadaUserRepository;
import org.reggsoft.srfcore.persistance.entity.Privilege;
import org.reggsoft.srfcore.persistance.entity.Role;
import org.reggsoft.srfcore.persistance.entity.Role;
import org.reggsoft.srfcore.persistance.entity.ScadaUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.*;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {

    boolean alreadySetup = false;

    @Autowired
    private ScadaUserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup)
            return;
        Privilege readPrivilege
                = createPrivilegeIfNotFound("READ_PRIVILEGE");
        Privilege writePrivilege
                = createPrivilegeIfNotFound("WRITE_PRIVILEGE");

        List<Privilege> adminPrivileges = Arrays.asList(
                readPrivilege, writePrivilege);
        createRoleIfNotFound("ROLE_ADMIN", adminPrivileges);
        createRoleIfNotFound("ROLE_USER", Arrays.asList(readPrivilege));

        Role adminRole = roleRepository.findByName("ROLE_ADMIN");
        ScadaUser user = new ScadaUser();
//        user.setId(0L);
        user.setUsername("admin");
        user.setFirstName("Scada");
        user.setLastName("Administrator");
        user.setPassword(passwordEncoder.encode("admin"));
        user.setEmail("admin@test.com");
//        Set<Role> roles = new HashSet<>();
//        roles.add(new Role("ROLE_ADMIN"));
        user.setRoles(Arrays.asList(adminRole));
//        user.setAuthorities(roles);
        user.setEnabled(true);
        userRepository.save(user);

        ScadaUser normalUser = new ScadaUser();
//        normalUser.setId(1L);
        normalUser.setUsername("user");
        normalUser.setFirstName("John");
        normalUser.setLastName("Snow");
        normalUser.setPassword(passwordEncoder.encode("user"));
        normalUser.setEmail("john@snow.org");
        normalUser.setEnabled(true);
        normalUser.setRoles(Arrays.asList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(normalUser);

        alreadySetup = true;

    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {

        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege(name);
            privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(
            String name, Collection<Privilege> privileges) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            roleRepository.save(role);
        }
        return role;
    }
}
