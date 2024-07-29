package fpt.lab.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class EmployeeDataSourceConfig {
    
    @Value("${employee.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${employee.datasource.url}")
    private String jdbcUrl;

    @Value("${employee.datasource.username}")
    private String username;

    @Value("${employee.datasource.password}")
    private String password;

    @Bean(name="employeeDataSource")
    public DataSource employeeDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    @Bean(name="employeeJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("employeeDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}

