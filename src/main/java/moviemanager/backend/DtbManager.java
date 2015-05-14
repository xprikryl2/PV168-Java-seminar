/*
 * Class managing operation in database like reading from dtb, writing to dtb and searching in dtb.
 */
package moviemanager.backend;

import common.ServiceFailureException;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.transaction.annotation.EnableTransactionManagement; 
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.LoggerFactory;
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
    final static org.slf4j.Logger log = LoggerFactory.getLogger(MovieManagerImpl.class);
    
    public static void main(String[] args) throws ServiceFailureException, ClassNotFoundException{
        try {
            System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("output.txt")), true));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DtbManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        log.info("main running");
        SpringConfig sc = new SpringConfig();
        MovieManagerImpl movieMngr = new MovieManagerImpl(sc.dataSource());
        movieMngr.listAllMovies();
        
        log.info("halooo");
        PersonManagerImpl mngr = (PersonManagerImpl) sc.personManager();
        log.debug("adsasdasd");
    }
    
    
    
}
