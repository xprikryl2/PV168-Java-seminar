/*
 * Class managing operation in database like reading from dtb, writing to dtb and searching in dtb.
 */
package moviemanager.backend;

import common.ServiceFailureException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        SpringConfig sc = new SpringConfig();
        
        PersonManagerImpl mngr = (PersonManagerImpl) sc.personManager();
        System.out.println("person: " + mngr.getPerson(1l).toString());
        DataSource d = null;
        if (d == null){System.out.println ("errrror");}
        d = sc.dataSource();
        if (d == null){System.out.println ("errrror");}
                System.out.println("this is main");
        try {
            Connection c = d.getConnection();
            Properties p = c.getClientInfo();
            p.toString();
            
        } catch (SQLException ex) {
            Logger.getLogger(DtbManager.class.getName()).log(Level.SEVERE, "tu to spadlo", ex);
        }
    }
    
    
    
}
