package org.reggsoft.srfcore.persistance.dao;

import org.reggsoft.srfcore.persistance.entity.ScadaUser;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

public interface ScadaUserRepository extends JpaRepository<ScadaUser, Long> {

    public  ScadaUser findByUsername(String username);
}
