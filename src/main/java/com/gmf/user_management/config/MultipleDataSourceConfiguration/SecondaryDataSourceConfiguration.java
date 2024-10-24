package com.gmf.user_management.config.MultipleDataSourceConfiguration;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class SecondaryDataSourceConfiguration {
    //Secondary Configuration Multiple Datasource
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties internalDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    public DataSource internalDataSource() {
        return internalDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean
    @ConfigurationProperties("spring.partner-datasource")
    public DataSourceProperties externalDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource externalDataSource() {
        return externalDataSourceProperties().initializeDataSourceBuilder().build();
    }
}
