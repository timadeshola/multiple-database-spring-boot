package com.example.multipledatabasespringboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import javax.persistence.EntityManager;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 9:52 AM
 */
@TestConfiguration
public class TestConfig {

    private final EntityManager mysqlEntityManager;
    private final EntityManager postgresEntityManager;
    private final CustomEntityManager customEntityManager;

    @Autowired
    public TestConfig(@Qualifier("mysqlEntityManagerFactoryTest") EntityManager mysqlEntityManager,
                      @Qualifier("postgresEntityManagerFactoryTest") EntityManager postgresEntityManager,
                      CustomEntityManager customEntityManager) {
        this.mysqlEntityManager = mysqlEntityManager;
        this.postgresEntityManager = postgresEntityManager;
        this.customEntityManager = customEntityManager;
    }


    @Bean
    public CustomDatabaseBean customDatabaseBean() {
        return new CustomDatabaseBean(mysqlEntityManager, postgresEntityManager, customEntityManager);
    }
}