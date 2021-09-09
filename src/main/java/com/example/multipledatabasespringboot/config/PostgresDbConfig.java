package com.example.multipledatabasespringboot.config;

import com.example.multipledatabasespringboot.model.config.PostgresConfig;
import lombok.RequiredArgsConstructor;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:04 AM
 */
@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(entityManagerFactoryRef = "postgresEntityManagerFactory", basePackages = "com.example.multipledatabasespringboot.persistence.postgres.repository")
@EnableTransactionManagement
public class PostgresDbConfig {

    private final PostgresConfig config;

    @Bean("postgresDS")
    public DataSource postgresDataSource() {
        return DataSourceBuilder.create()
                .driverClassName(config.getDriverClassName())
                .url(config.getUrl())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();
    }

    @Bean("postgresEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean postgresEntityManager(@Qualifier("postgresDS") DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setPackagesToScan("com.example.multipledatabasespringboot.persistence.postgres.entity");
        em.setPersistenceUnitName("postgresPU");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform(config.getDatabasePlatform());
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(jpaProperties());
        return em;
    }

    @Bean("postgresTransactionManager")
    public PlatformTransactionManager postgresTransactionManager(@Qualifier("postgresEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean("postgresJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("postgresDS") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", config.getDialect());
        properties.put("hibernate.show_sql", config.getShowSql());
        properties.put("hibernate.ddl-auto", config.getDdlAuto());
        return properties;
    }

}