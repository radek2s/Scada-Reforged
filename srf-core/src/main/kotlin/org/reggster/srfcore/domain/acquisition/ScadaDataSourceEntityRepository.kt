package org.reggster.srfcore.domain.acquisition

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ScadaDataSourceEntityRepository<T: ScadaDataSourceEntity<*>, ID: java.io.Serializable>: JpaRepository<T, ID> { }