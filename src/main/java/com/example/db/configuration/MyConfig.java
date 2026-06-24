package com.example.db.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import com.example.db.entity.MyEntity;
import com.example.db.repository.MyRepo;
import com.example.db.repository.MyRepoT;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "db1EntityManagerFactory", transactionManagerRef = "db1TransactionManager", basePackages = "com.example.db.repository", includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MyRepo.class), excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MyRepoT.class), considerNestedRepositories = false)
@EnableTransactionManagement
public class MyConfig {

    @Primary
    @Bean(name = "db1DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.db1")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean(name = "db1DataSource")
    public DataSource dataSource(@Qualifier("db1DataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(name = "db1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("db1DataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages(MyEntity.class)
                .persistenceUnit("db1")
                .build();

    }

    @Primary
    @Bean(name = "db1TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}