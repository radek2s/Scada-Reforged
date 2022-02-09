package org.reggsoft.srfcore.acl;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.reggsoft.srfcore.persistance.dao")
@EntityScan(basePackages = {"org.reggsoft.srfcore.persistance.entity"})
public class JPAPersistenceConfig {
}