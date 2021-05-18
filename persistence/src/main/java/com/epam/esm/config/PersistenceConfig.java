package com.epam.esm.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@EnableAutoConfiguration
@EntityScan(basePackages = {"com.epam.esm.entity","com.epam.esm.audit"})
@ComponentScan(basePackages = "com.epam.esm")
@PropertySource("classpath:persistence.properties")
public class PersistenceConfig {
}
