/*
 * Class managing operation in database like reading from dtb, writing to dtb and searching in dtb.
 */
package com.mycompany.moviemanager;

import static com.mycompany.moviemanager.PersonManagerImpl.log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class DtbManager {
    private static final String LOGIN = "administrator";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    
    public static void main(String[] args) throws ServiceFailureException, ClassNotFoundException{
        System.out.println("this is main");
        PersonManagerImpl mngr = new PersonManagerImpl(null);
        //Calendar calendar = new GregorianCalendar(1992,0,31);
        //    List<Movie> mov = new ArrayList<>();
         //   mov.add(new Movie());
            
           // Person person = new Person("Mlcit", calendar, mov);
        
        //mngr.addPerson(person);
        //System.out.println("ID: " + person.getId());
        //mngr.removePerson(103);
        //System.out.println (p.getName());

        //Create tables
        //try{createTablePersons();       } catch(ClassNotFoundException ex){}
        //try{createTableMovies();        } catch(ClassNotFoundException ex){}
        //try{createTableRelationships(); } catch(ClassNotFoundException ex){}
        
        //Drop tables
        //try{dropTablePersons();         } catch(ClassNotFoundException ex){}
        //try{dropTableMovies();          } catch(ClassNotFoundException ex){}
        //try{dropTableRelationships();   } catch(ClassNotFoundException ex){}
                
    }
}
