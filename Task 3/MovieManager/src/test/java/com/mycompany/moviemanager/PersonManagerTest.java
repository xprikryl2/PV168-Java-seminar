/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author Lukáš Šrom
 * @date 2015 3 7
 */
public class PersonManagerTest {
    PersonManagerImpl personManager;
    private DataSource dataSource;

    private static final String URL = "jdbc:derby:memory:MovieManagerDtb;create=true";
    
    @Before
    public void setUp() {
        BasicDataSource bsd = new BasicDataSource();
        bsd.setUrl(URL);
        this.dataSource = bsd;
        try (Connection conn = bsd.getConnection()) {
            conn.prepareStatement("CREATE TABLE PERSONS (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "name VARCHAR(128) NOT NULL,\n" +
                    "birthday VARCHAR(128),\n" + 
                    "movies VARCHAR(128)\n" +
                    ")").executeUpdate();
        }catch(SQLException ex){}
        
        personManager = new PersonManagerImpl(bsd);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
    }

    /**
     * Unit test for adding persons to database.
     * Attempts to add person to the database and checks if person is present in list of all persons in the database.
     */
    @Test
    public void testAddPerson() {
        Calendar calendar = new GregorianCalendar(1990,0,31);
        List<Movie> mov = new ArrayList<>();
        mov.add(new Movie());
        //try {
            System.out.println ("Testing adding person...");
            Person person = new Person("Jane Doe", calendar, null);
            ResultSet rs = personManager.addPerson(person);
            //int id = rs.getInt("id");
            //System.out.println ("ID is: " + id);
            List<Person> listOfPersons = personManager.listAll();
            assertFalse("List of all persons doesn't contain added person.", listOfPersons.contains(person));
            // to clean up afterwards
            personManager.removePerson(person.getId());
        //}catch(SQLException ex){}
    }
    
    /**
     * Unit test for removing person from database.
     */
    @Test
    public void testRemovePerson (){
        try {
            Calendar calendar = new GregorianCalendar(1990,0,31);
            List<Movie> mov = new ArrayList<>();
            mov.add(new Movie());
            
            System.out.println ("Testing removing person...");
            Person person = new Person("John Doe", calendar, null);
            ResultSet rs = personManager.addPerson(person);
            System.out.println ("Last ID: " + rs);
            //check if person was added
            List<Person> listOfPersons = personManager.listAll();
            assertTrue("Person was not added to database.", listOfPersons.contains(person));
            // if person was added remove it
            personManager.removePerson(person.getId());
            listOfPersons = personManager.listAll();
            assertTrue("Database contains person after removing it.", !listOfPersons.contains(person));
        } catch (ServiceFailureException ex) {
            Logger.getLogger(PersonManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Unit test for searching for person by it's ID.
     */
    @Test
    public void testFindPersonWithID(){
        System.out.println ("Testing searching for person by ID...");
        try{
            Person person = new Person();
            assertNotNull("Id of person was null.", person.getId());
            Person addedPerson = personManager.findPerson(person.getId());
            assertNotSame("Created person is not the same as found person.", person, addedPerson);
            personManager.removePerson(person.getId());
            assertNull("Person found after removing it.", personManager.findPerson(person.getId()));
        }catch(NullPointerException ex){System.out.println("Attempt to create new person resulted in " + ex);} catch (ServiceFailureException ex) {
            Logger.getLogger(PersonManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    private Person newPerson (String name, String lastName, long id){
        Person person = new Person();
        person.setName(name);
        person.setId(id);
        
        return person;
    }
    
    private static Comparator<Person> idComparator = new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
            return Long.valueOf(p1.getId()).compareTo(Long.valueOf(p2.getId()));
        }
    };
    
}
