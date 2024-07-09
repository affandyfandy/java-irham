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
public class ContactDataSourceConfig {
    
    @Value("${contact.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${contact.datasource.url}")
    private String jdbcUrl;

    @Value("${contact.datasource.username}")
    private String username;

    @Value("${contact.datasource.password}")
    private String password;

    @Bean(name="contactDataSource")
    public DataSource contactDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(jdbcUrl);
        config.setUsername(username);
        config.setPassword(password);
        return new HikariDataSource(config);
    }

    @Bean(name="contactJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("contactDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}