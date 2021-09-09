package com.example.multipledatabasespringboot.config;

import com.example.multipledatabasespringboot.persistence.mysql.repository.UserRepository;
import com.example.multipledatabasespringboot.persistence.postgres.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManager;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:19 AM
 */
@Configuration
public class CustomDatabaseBean {

    private final EntityManager mysqlEntityManager;
    private final EntityManager postgresEntityManager;
    private final CustomEntityManager customEntityManager;

    @Autowired
    public CustomDatabaseBean(@Qualifier("mysqlEntityManagerFactory") EntityManager mysqlEntityManager,
                              @Qualifier("postgresEntityManagerFactory") EntityManager postgresEntityManager,
                              CustomEntityManager customEntityManager) {
        this.mysqlEntityManager = mysqlEntityManager;
        this.postgresEntityManager = postgresEntityManager;
        this.customEntityManager = customEntityManager;
    }

    @Bean
    public UserRepository userRepositoryEm() {
        return customEntityManager.getJpaFactory(mysqlEntityManager).getRepository(UserRepository.class);
    }

    // PostgresDB ENTITIES

    @Bean
    public CustomerRepository customerRepositoryEm() {
        return customEntityManager.getJpaFactory(postgresEntityManager).getRepository(CustomerRepository.class);
    }

}