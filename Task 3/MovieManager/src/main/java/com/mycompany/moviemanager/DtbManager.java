/*
 * Class managing operation in database like reading from dtb, writing to dtb and searching in dtb.
 */
package com.mycompany.moviemanager;

import static com.mycompany.moviemanager.PersonManagerImpl.log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

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

        //Create tables
        //try{createTablePersons();       } catch(ClassNotFoundException ex){}
        //try{createTableMovies();        } catch(ClassNotFoundException ex){}
        //try{createTableRelationships(); } catch(ClassNotFoundException ex){}
        
        //Drop tables
        //try{dropTablePersons();         } catch(ClassNotFoundException ex){}
        //try{dropTableMovies();          } catch(ClassNotFoundException ex){}
        //try{dropTableRelationships();   } catch(ClassNotFoundException ex){}
                
    }
    
    public static void createTablePersons() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("CREATE TABLE PERSONS (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "name VARCHAR(128) NOT NULL,\n" +
                    "birthday VARCHAR(128)\n" +
                    ")").executeUpdate();
        }catch (SQLException ex){
            log.error("Error when creating table PERSONS!", ex);
            throw new ServiceFailureException("Error when creating table PERSONS!", ex);
        };
    }
    
    public static void dropTablePersons() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("DROP TABLE PERSONS").executeUpdate();
        }catch (SQLException ex){
            log.error("Error when dropping table PERSONS!", ex);
            throw new ServiceFailureException("Error when dropping table PERSONS!", ex);
        };
    }
    
    public static void createTableMovies() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("CREATE TABLE MOVIES (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "title VARCHAR(128) NOT NULL,\n" +
                    "movieYear INTEGER,\n" + 
                    "genre VARCHAR(128),\n" +
                    "length INTEGER\n" +
                    ")").executeUpdate();
        }catch (SQLException ex){
            log.error("Error when creating table MOVIES!", ex);
            throw new ServiceFailureException("Error when creating table MOVIES!", ex);
        };
    }
    
        public static void dropTableMovies() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("DROP TABLE MOVIES").executeUpdate();
        }catch (SQLException ex){
            log.error("Error when dropping table MOVIES!", ex);
            throw new ServiceFailureException("Error when dropping table MOVIES!", ex);
        };
    }
    
    public static void createTableRelationships() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("CREATE TABLE RELATIONSHIPS (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "movieId INTEGER NOT NULL,\n" +
                    "personId INTEGER NOT NULL,\n" + 
                    "director BOOLEAN NOT NULL,\n" + 
                    "writer BOOLEAN NOT NULL,\n" + 
                    "movieCast BOOLEAN NOT NULL\n" +
                    ")").executeUpdate();
        }catch (SQLException ex){
            log.error("Error when creating table RELATIONSHIPS!", ex);
            throw new ServiceFailureException("Error when creating table RELATIONSHIPS!", ex);
        };
    }
    
    public static void dropTableRelationships() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("DROP TABLE RELATIONSHIPS").executeUpdate();
        }catch (SQLException ex){
            log.error("Error when dropping table RELATIONSHIPS!", ex);
            throw new ServiceFailureException("Error when dropping table RELATIONSHIPS!", ex);
        };
    }
}
