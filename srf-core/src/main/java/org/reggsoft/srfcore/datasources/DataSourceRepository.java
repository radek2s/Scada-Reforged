package org.reggsoft.srfcore.datasources;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataSourceRepository extends JpaRepository<AbstractDataSource, Long> {
}
