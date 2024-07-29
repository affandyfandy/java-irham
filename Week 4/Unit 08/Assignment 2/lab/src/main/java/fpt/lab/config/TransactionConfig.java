package fpt.lab.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    
    @Bean
    public PlatformTransactionManager employeeTransactionManager(DataSource employeeDataSource) {
        return new DataSourceTransactionManager(employeeDataSource);
    }

    @Bean
    public PlatformTransactionManager contactTransactionManager(DataSource contactDataSource) {
        return new DataSourceTransactionManager(contactDataSource);
    }

    @Bean
    public TransactionTemplate employeeTransactionTemplate(PlatformTransactionManager employeeTransactionManager) {
        return new TransactionTemplate(employeeTransactionManager);
    }

    @Bean
    public TransactionTemplate contactTransactionTemplate(PlatformTransactionManager contactTransactionManager) {
        return new TransactionTemplate(contactTransactionManager);
    }
}
