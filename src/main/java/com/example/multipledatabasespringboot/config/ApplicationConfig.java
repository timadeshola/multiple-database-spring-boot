package com.example.multipledatabasespringboot.config;

import com.example.multipledatabasespringboot.model.config.MySqlConfig;
import com.example.multipledatabasespringboot.model.config.PostgresConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Project title: multiple-database-spring-boot
 *
 * @author johnadeshola
 * Date: 9/9/21
 * Time: 8:06 AM
 */
@Configuration
@EnableConfigurationProperties({MySqlConfig.class, PostgresConfig.class})
public class ApplicationConfig {


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}