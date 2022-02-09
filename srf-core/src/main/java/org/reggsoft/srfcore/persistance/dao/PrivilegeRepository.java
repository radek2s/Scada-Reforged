package org.reggsoft.srfcore.persistance.dao;

import org.reggsoft.srfcore.persistance.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    public Privilege findByName(String name);
}
