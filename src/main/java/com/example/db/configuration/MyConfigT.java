package com.example.db.configuration;

import java.util.HashMap;

import javax.sql.DataSource;

import com.example.db.entity.MyEntityT;
import com.example.db.repository.MyRepo;
import com.example.db.repository.MyRepoT;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "db2EntityManagerFactory", transactionManagerRef = "db2TransactionManager", basePackages = "com.example.db.repository", includeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MyRepoT.class), excludeFilters = @Filter(type = FilterType.ASSIGNABLE_TYPE, classes = MyRepo.class), considerNestedRepositories = false)
@EnableTransactionManagement
public class MyConfigT {

    @Bean(name = "db2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.db2")
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "db2DataSource")
    public DataSource dataSource(@Qualifier("db2DataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "db2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder,
            @Qualifier("db2DataSource") DataSource dataSource) {

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");

        return builder.dataSource(dataSource)
                .packages(MyEntityT.class)
                .persistenceUnit("db2")
                .properties(properties)
                .build();
    }

    @Bean(name = "db2TransactionManager")
    public PlatformTransactionManager transactionManager(
            @Qualifier("db2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
