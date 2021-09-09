package com.example.multipledatabasespringboot.config;

import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:18 AM
 */
@Service
public class CustomEntityManager {

    public JpaRepositoryFactory getJpaFactory(EntityManager em) {
        return new JpaRepositoryFactory(em);
    }

}