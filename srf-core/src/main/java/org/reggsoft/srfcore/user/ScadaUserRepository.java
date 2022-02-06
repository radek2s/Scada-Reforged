package org.reggsoft.srfcore.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScadaUserRepository extends JpaRepository<ScadaUser, Long> {

    public  ScadaUser findByUsername(String username);
}
