/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
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
    //private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    
    @Before
    public void setUp() {
        BasicDataSource ds = new BasicDataSource();
        ds.setUsername("administrator");
        ds.setPassword("admin");
        ds.setUrl(URL);
        dataSource = ds;
        
        try (Connection conn = dataSource.getConnection()) {
            conn.prepareStatement("CREATE TABLE PERSONS (\n" +
                    "id INTEGER NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY,\n" +
                    "name VARCHAR(128) NOT NULL,\n" +
                    "birthday VARCHAR(128)\n" + 
                    ")").executeUpdate();
        }catch(SQLException ex){}
        
        personManager = new PersonManagerImpl(dataSource);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
    }

    /**
     * Unit test for adding persons to database.
     * Attempts to add person to the database and checks if person is present in list of all persons in the database.
     */
    @Test
    public void testAddPerson() {
        System.out.println ("Testing adding person...");
        
        Calendar calendar = new GregorianCalendar(1995,11,1);        
            
        Person person = new Person("Jane Doe (Add)", calendar);
        personManager.createPerson(person);

        Long id = person.getId();
        assertNotNull(id);
        Person result = personManager.getPerson(id);
        assertEquals(person, result);
        assertNotSame(person, result);
    }
    
    /**
     * Unit test for removing person from database.
     */
    @Test
    public void testRemovePerson (){
        System.out.println ("Testing removing person...");
        try {
            Calendar calendar = new GregorianCalendar(1987,5,17);            
            Person person = new Person("John Doe (Remove)", calendar);
            
            personManager.createPerson(person);
            //check if person was added
            Person res = personManager.getPerson(person.getId());
            assertNotNull(res);
            assertTrue(person.equals(res));
            // if person was added remove it
            personManager.deletePerson(person.getId());
            assertNull(personManager.getPerson(person.getId()));
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
            Calendar calendar = new GregorianCalendar(1933,0,9);
            Person person = new Person("Wilbur Smith (Find)", calendar);
            
            personManager.createPerson(person);
            Person addedPerson = personManager.getPerson(person.getId());
            assertNotSame("Created person is not the same as found person.", person, addedPerson);
            personManager.deletePerson(person.getId());
            assertNull("Person found after removing it.", personManager.getPerson(person.getId()));
        }catch(NullPointerException ex){System.out.println("Attempt to create new person resulted in " + ex);} catch (ServiceFailureException ex) {
            Logger.getLogger(PersonManagerTest.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
    
    /**
     * Unit test for updating person.
     */
    @Test
    public void testUpdatePerson (){
        System.out.println ("Testing updating person...");
        
        Calendar calendar = new GregorianCalendar(1956, 9, 19);
        Calendar updatedCalendar = new GregorianCalendar(1955, 9, 19);
        Person person = new Person("James T. Kirk (Update)", calendar);
        Person updatedPerson = new Person("James Tiberius Kirk", updatedCalendar);
        
        personManager.createPerson(person);
        Long id = person.getId();
        Person res = personManager.getPerson(id);
        res.setName("James Tiberius Kirk");
        res.setBirth(calendar);
        res.setId(person.getId());
        personManager.updatePerson(res);
        assertEquals(person.getName(), res.getName());
        
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
