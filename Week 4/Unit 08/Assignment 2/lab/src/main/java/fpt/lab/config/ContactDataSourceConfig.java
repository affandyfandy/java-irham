package fpt.lab.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class ContactDataSourceConfig {
    
    @Bean(name="contactDataSource")
    public DataSource contactDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/unit5");
        config.setUsername("root");
        config.setPassword("");
        return new HikariDataSource(config);
    }

    @Bean(name="contactJdbcTemplate")
    public JdbcTemplate jdbcTemplate(@Qualifier("contactDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
