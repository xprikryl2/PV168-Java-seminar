/*
 * Class managing operation in database like reading from dtb, writing to dtb and searching in dtb.
 */
package com.mycompany.moviemanager;

import static com.mycompany.moviemanager.PersonManagerImpl.log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

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
    
    public static void main(String[] args){
        System.out.println("this is main");
        
        try{
            createTablePersons();
        }catch(ClassNotFoundException ex){}
        try{
            createTableMovies();
        }catch(ClassNotFoundException ex){}
    }
    
    public static void createTablePersons() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("CREATE TABLE PERSONS (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "name VARCHAR(128) NOT NULL,\n" +
                    "birthday VARCHAR(128),\n" + 
                    "movies VARCHAR(128)\n" +
                    ")").executeUpdate();
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when creating table PERSONS!", ex);
        };
    }
    
    public static void createTableMovies() throws ClassNotFoundException{
        // to connect to dtb
        Class.forName(DRIVER);
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);){
            conn.prepareStatement("CREATE TABLE PERSONS (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "title VARCHAR(128) NOT NULL,\n" +
                    "movieYear VARCHAR(128),\n" + 
                    "genre VARCHAR(128),\n" +
                    "length INTEGER,\n" +
                    "director VARCHAR(128),\n" +
                    "writer VARCHAR(128),\n" +
                    "movieCast VARCHAR(256)\n" +
                    ")").executeUpdate();
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when creating table PERSONS!", ex);
        };
    }
}
