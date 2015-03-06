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
 * @author Lukáš
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

    @Test
    public void testPerson() {
        System.out.println("Person");
        List<Movie> movie = null;
        //Calendar.Builder birth = new GregorianCalendar.Builder().setDate(1987, 1, 21);
        Calendar birth = Calendar.getInstance();
        birth.set(1987, 2, 17);
        Person personOne = new Person("John", "Smith", birth, movie);
        Person personTwo = new Person("John", "Smith", birth, movie);
        Person personThree = new Person ("Jane", "Smith", birth, movie);
        
        assertEquals(personOne.getFirstName(), personTwo.getFirstName());
            System.out.println ("PersonOne name is: " + personOne.getFirstName());
        assertEquals(personOne.getLastName(), personTwo.getLastName());
            System.out.println ("PersonOne last name is: " + personOne.getLastName());
        assertEquals(personOne.getBirth(), personTwo.getBirth());
            System.out.println ("PersonOne date of birth is: " + personOne.getBirth());
        assertEquals(personOne.getBirth(), birth);
            System.out.println ("Date of birth of personOne matches inpput parameter.");
        assertNotSame(personOne.getIdPerson(), personTwo.getIdPerson());
            System.out.println ("ID of personOne is: " + personOne.getIdPerson() + " and ID of personTwo is: " + personTwo.getIdPerson());
        
        assertEquals(personThree.getFirstName(), "Jane");
        assertEquals(personThree.getLastName(), "Smith");
        assertEquals(personThree.getBirth(), birth);
        assertTrue(personThree.getIdPerson() > 0);
        assertNotSame(personThree, personTwo);
        
        assertNotNull(personOne.getIdPerson());
        assertNotNull(personTwo.getIdPerson());
        assertNotNull(personThree.getIdPerson());
        
    
    }
}
