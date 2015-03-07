/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;

import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lukáš Šrom
 * @date 2015 3 7
 */
public class PersonManagerTest {
    PersonManager personManager;
    
    public PersonManagerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Unit test for adding persons to database.
     * Attempts to add person to the database and checks if person is present in list of all persons in the database.
     */
    @Test
    public void testAddPerson() {
        System.out.println ("Testing adding person...");
        Person person = new Person();
        personManager.addPerson(person);
        List<Person> listOfPersons = personManager.listAll();
        assertTrue("List of all persons doesn't contain added person.", listOfPersons.contains(person));
        // to clean up afterwards
        personManager.removePerson(person);
    }
    
    /**
     * Unit test for removing person from database.
     */
    @Test
    public void testRemovePerson (){
        System.out.println ("Testing removing person...");
        Person person = new Person();
        personManager.addPerson(person);
        //check if person was added
        List<Person> listOfPersons = personManager.listAll();
        assertTrue("Person was not added to database.", listOfPersons.contains(person));
        // if person was added remove it
        personManager.removePerson(person);
        listOfPersons = personManager.listAll();
        assertTrue("Database contains person after removing it.", !listOfPersons.contains(person));
    }
    
    /**
     * Unit test for searching for person by it's ID.
     */
    @Test
    public void testFindPersonWithID(){
        System.out.println ("Testing searching for person by ID...");
        try{
            Person person = new Person();
            assertNotNull("Id of person was null.", person.getIdPerson());
        Person addedPerson = personManager.findPerson(person.getIdPerson());
        assertNotSame("Created person is not the same as found person.", person, addedPerson);
        personManager.removePerson(person);
        assertNull("Person found after removing it.", personManager.findPerson(person.getIdPerson()));
        }catch(NullPointerException ex){System.out.println("Attempt to create new person esulted in" + ex);}
        
    }
    
}
