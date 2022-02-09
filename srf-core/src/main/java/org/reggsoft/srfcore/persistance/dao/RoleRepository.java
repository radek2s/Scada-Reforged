package org.reggsoft.srfcore.persistance.dao;

import org.reggsoft.srfcore.persistance.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    public Role findByName(String name);
}
