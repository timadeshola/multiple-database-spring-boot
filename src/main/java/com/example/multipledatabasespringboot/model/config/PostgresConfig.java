package com.example.multipledatabasespringboot.model.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

import java.io.Serializable;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:05 AM
 */
@ConstructorBinding
@ConfigurationProperties(prefix = "spring.datasource.postgres")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PostgresConfig implements Serializable {

    private String username;
    private String password;
    private String driverClassName;
    private String url;
    private String dialect;
    private String ddlAuto;
    private Boolean showSql;
    private String databasePlatform;
}