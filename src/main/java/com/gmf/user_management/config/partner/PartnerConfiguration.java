package com.gmf.user_management.config.partner;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * The PartnerConfiguration class is responsible for configuring two separate databases in a Spring Boot application:
 * PostgreSQL → The primary database.
 * MySQL → A secondary database for external data (likely related to partners).
 * It also configures transaction managers for both databases to ensure proper transaction handling.
 */

@Component
@Configuration
@EnableTransactionManagement
public class PartnerConfiguration {

    /**
     * Defines the primary DataSource for the application using PostgreSQL.
     * This DataSource is configured using properties from `spring.datasource.*`.
     * The @Primary annotation ensures that this is the default DataSource used
     * when multiple DataSources exist.
     */
    @Primary
    @Bean(name = "postgresDataSource")
    public DataSource postgresDataSource(@Value("${spring.datasource.url}") String url,
                                         @Value("${spring.datasource.username}") String username,
                                         @Value("${spring.datasource.password}") String password,
                                         @Value("${spring.datasource.driverClassName}") String driverClassName) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    /**
     * Defines a secondary DataSource for the application using MySQL.
     * This DataSource is configured using properties from `spring.partner-datasource.*`.
     * It does not have the @Primary annotation, meaning it will not be the default
     * DataSource when multiple exist.
     */
    @Bean(name = "mysqlDataSource")
    public DataSource mysqlDataSource(@Value("${spring.partner-datasource.url}") String url,
                                      @Value("${spring.partner-datasource.username}") String username,
                                      @Value("${spring.partner-datasource.password}") String password,
                                      @Value("${spring.partner-datasource.driver-class-name}") String driverClassName) {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }

    /**
     * Configures a transaction manager for PostgreSQL, ensuring transaction
     * management is handled using the primary DataSource (`postgresDataSource`).
     * The @Primary annotation ensures this is the default transaction manager.
     */
    @Primary
    @Bean(name = "postgresTransactionManager")
    public DataSourceTransactionManager postgresTransactionManager(
            @Qualifier("postgresDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * Configures a transaction manager for MySQL, using the `mysqlDataSource`.
     * This transaction manager is explicitly defined and does not override the primary one.
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(
            @Qualifier("mysqlDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


}
