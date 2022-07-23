package org.reggster.srfcore.domain.datasources

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean

@NoRepositoryBean
interface ScadaDataSourceRepository<T: ScadaDataSource, ID: java.io.Serializable>: JpaRepository<T,ID> { }