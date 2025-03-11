package com.fmsp.medical_appointment.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.fmsp.medical_appointment.repository.jpa.mysql",
        entityManagerFactoryRef = "mysqlEntityManagerFactory",
        transactionManagerRef = "mysqlTransactionManager"
)
public class MySQLConfig {

    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/notificaciones_db?serverTimezone=UTC");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("");
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        hikariConfig.setMaximumPoolSize(5);
        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "mysqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean mysqlEntityManagerFactory(
            @Qualifier("mysqlDataSource") DataSource mysqlDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mysqlDataSource);
        em.setPackagesToScan("com.fmsp.medical_appointment.entity.core.mysql");
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean(name = "mysqlTransactionManager")
    public JpaTransactionManager mysqlTransactionManager(
            @Qualifier("mysqlEntityManagerFactory") EntityManagerFactory mysqlEntityManagerFactory) {
        return new JpaTransactionManager(mysqlEntityManagerFactory);
    }
}