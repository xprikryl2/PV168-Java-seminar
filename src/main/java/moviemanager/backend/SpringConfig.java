/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import static org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType.DERBY;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for Spring JDBC.
 * @author Lukas Srom <lukas.srom@gmail.com>
 */
//@Configuration
@EnableTransactionManagement
//@PropertySource("classpath:myconf.properties")
public class SpringConfig {
    public SpringConfig (){}
    
    @Autowired
        Environment env;
 
        @Bean
        public DataSource dataSource() {
            /*BasicDataSource bds = new BasicDataSource(); //Apache DBCP connection pooling DataSource
            bds.setDriverClassName(env.getProperty("jdbc.driver"));
            bds.setUrl(env.getProperty("jdbc.url"));
            bds.setUsername(env.getProperty("jdbc.user"));
            bds.setPassword(env.getProperty("jdbc.password"));
            return bds;*/
            /*Properties conf = new Properties();
            BasicDataSource ds = new BasicDataSource();
            try {            
                conf.load(SpringConfig.class.getResourceAsStream("/myconf.properties"));            
            } catch (IOException ex) {
            //log.error("Loading properties has failed!");
            }
            try {
                DriverManager.getConnection(conf.getProperty("jdbc.url"));
            
            } catch (SQLException ex) {
                try {
                    Connection conn = DriverManager.getConnection(conf.getProperty("jdbc.url"), conf.getProperty("jdbc.user"), conf.getProperty("jdbc.password"));
                    //Connection conn = DriverManager.getConnection("org.apache.jdbc.ClientDriver" + "create=true;", "administrator", "admin");
                } catch (SQLException ex1) {
                    Logger.getLogger(SpringConfig.class.getName()).log(Level.SEVERE, null, ex1);
                }
        }
        
        ds.setUrl(conf.getProperty("jdbc.url"));      
        
        return ds;*/
            Properties conf = new Properties();
            BasicDataSource ds = new BasicDataSource();
            try {
                conf.load(SpringConfig.class.getResourceAsStream("/myconf.properties"));
            } catch (IOException ex) {
            //log.error("Loading properties has failed!");
            }
            ds.setUrl(conf.getProperty("jdbc.url"));
            ds.setDriverClassName(conf.getProperty("jdbc.driver"));
            ds.setUsername(conf.getProperty("jdbc.user"));
            ds.setPassword(conf.getProperty("jdbc.password"));
            
            return ds;
/*
            BasicDataSource bds = new BasicDataSource();
            bds.setDriverClassName("org.apache.derby.jdbc.ClientDriver");
            bds.setUrl("jdbc:derby://localhost:1527/MovieManagerDtb");
            bds.setUsername("administrator");
            bds.setPassword("admin");
            return bds;*/
            
            /*return new EmbeddedDatabaseBuilder()
                .setType(DERBY)
                .build();*/
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
