package org.reggsoft.srfcore.persistance.dao;

import org.reggsoft.srfcore.persistance.entity.DataSourceVirtual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DataSourceVirtualRepository extends JpaRepository<DataSourceVirtual, Long> {

    List<DataSourceVirtual> findAll();

    DataSourceVirtual save(@Param("dataSourceVirtual") DataSourceVirtual dataSourceVirtual);
}
