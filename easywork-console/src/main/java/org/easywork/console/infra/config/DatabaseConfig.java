package org.easywork.console.infra.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 数据库配置
 * 
 * @author fiveupup
 * @version 1.0.0
 * @date 2025/09/09
 */
@Configuration
public class DatabaseConfig {
    
    /**
     * 主数据源
     */
    @Bean(name = "primaryDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .build();
    }
    
    // 如果需要多数据源，可以在这里添加其他数据源
    // @Bean(name = "secondaryDataSource")
    // @ConfigurationProperties(prefix = "spring.datasource.secondary")
    // public DataSource secondaryDataSource() {
    //     return DataSourceBuilder.create()
    //             .type(HikariDataSource.class)
    //             .build();
    // }
}