/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
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
public class PersonTest {
    
    public PersonTest() {
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
     * Unit test for non-parametric constructor of class Person.
     */
    @Test
    public void testPerson() {
        System.out.println("Testing class Person");
        List<Movie> movie = null;
        movie.add(new Movie());
        Calendar birth = Calendar.getInstance();
        birth.set(1987, 2, 17);
        Person personOne = new Person();
        Person personTwo = new Person();
        
        personOne.setFirstName("John");
        personOne.setLastName("Smith");
        personOne.setBirth(birth);
        
        assertEquals("Person's first name doesn't match.", personOne.getFirstName(), "John");
        assertEquals("Person's last name doesn't match.", personOne.getLastName(), "Smith");
        assertEquals("Person's date of birth doesn't match.", personOne.getBirth(), birth);
        assertNotNull("Person's ID is null.", personOne.getIdPerson());
        assertTrue("Person's ID is lower then 0.", personOne.getIdPerson() > 0);
        
        
        personTwo.setFirstName("John");
        personTwo.setLastName("Smith");
        personTwo.setBirth(birth);
        
        assertEquals("Person's first name doesn't match.", personTwo.getFirstName(), "John");
        assertEquals("Person's last name doesn't match.", personTwo.getLastName(), "Smith");
        assertEquals("Person's date of birth doesn't match.", personTwo.getBirth(), birth);
        assertNotNull("Person's ID is null.", personTwo.getIdPerson());
        assertTrue("Person's ID is lower then 0.", personTwo.getIdPerson() > 0);
        
        assertNotSame("Person One and Person Two are the same.", personOne, personTwo);    
    }
}
