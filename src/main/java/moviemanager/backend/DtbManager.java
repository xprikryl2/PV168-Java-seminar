/*
 * Class managing operation in database like reading from dtb, writing to dtb and searching in dtb.
 */
package moviemanager.backend;

import common.ServiceFailureException;
import org.springframework.transaction.annotation.EnableTransactionManagement; 
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 3 27
 */
public class DtbManager {
    //private static final String LOGIN = "administrator";
    //private static final String PASSWORD = "admin";
    //private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    //private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    
    public static void main(String[] args) throws ServiceFailureException, ClassNotFoundException{
        System.out.println("this is main");
        PersonManagerImpl mngr = new PersonManagerImpl(null);
                
    }
    
    @Configuration  //je to konfigurace pro Spring
    @EnableTransactionManagement //bude řídit transakce u metod označených @Transactional
    @PropertySource("classpath:myconf.properties") //načte konfiguraci z myconf.properties
    public static class SpringConfig {
 
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
 
        @Bean //potřeba pro @EnableTransactionManagement
        public PlatformTransactionManager transactionManager() {
            return new DataSourceTransactionManager(dataSource());
        }
 
        @Bean //náš manager, bude automaticky obalen řízením transakcí
        public PersonManager personManager() {
            return new PersonManagerImpl(dataSource());
        }
 
        @Bean
        public MovieManager movieManager() {
            // BookManagerImpl nepoužívá Spring JDBC, musíme mu vnutit spolupráci se Spring transakcemi
            return new MovieManagerImpl(new TransactionAwareDataSourceProxy(dataSource()));
        }
 
        @Bean
        public RelationshipManager relationManager() {
            RelationshipManagerImpl relationshipManager = new RelationshipManagerImpl(dataSource());
            
            return relationshipManager;
        }
    }
}
