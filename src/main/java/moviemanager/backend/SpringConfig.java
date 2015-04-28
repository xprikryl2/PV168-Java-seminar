/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for Spring JDBC.
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
@Configuration
@EnableTransactionManagement
@PropertySource("classpath:myconf.properties")
public class SpringConfig {
    
    @Autowired
        Environment env;
 
        @Bean
        public DataSource dataSource() {
            BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
            bds.setDriverClassName(env.getProperty("jdbc.driver"));
            bds.setUrl(env.getProperty("jdbc.url"));
            bds.setUsername(env.getProperty("jdbc.user"));
            bds.setPassword(env.getProperty("jdbc.password"));
            return bds;
        }
        
        @Bean // needed for @EnableTransactionManagement
        public PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }
 
        @Bean // manager with transaction control
        public PersonManager personManager() {
            return new PersonManagerImpl(dataSource());
        }
 
        @Bean
        public MovieManager movieManager() {
            return new MovieManagerImpl(new TransactionAwareDataSourceProxy(dataSource()));
        }
 
        @Bean
        public RelationshipManager relationManager() {
            RelationshipManagerImpl relationshipManager = new RelationshipManagerImpl(dataSource());
            
            return relationshipManager;
        }
    
}
