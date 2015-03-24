/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
        Calendar calendar = new GregorianCalendar(1990,0,31);
        List<Movie> mov = new ArrayList<>();
        mov.add(new Movie());
        Person personOne = new Person();
        Person personTwo = new Person();
        
        personOne.setName("John");
        personOne.setBirth(calendar);
        
        assertEquals("Person's first name doesn't match.", personOne.getName(), "John");
        assertEquals("Person's date of birth doesn't match.", personOne.getBirth(), calendar);
        assertNotNull("Person's ID is null.", personOne.getId());
        assertTrue("Person's ID is lower then 0.", personOne.getId() > 0);
        
        
        personTwo.setName("John");
        personTwo.setBirth(calendar);
        
        assertEquals("Person's first name doesn't match.", personTwo.getName(), "John");
        assertEquals("Person's date of birth doesn't match.", personTwo.getBirth(), calendar);
        assertNotNull("Person's ID is null.", personTwo.getId());
        assertTrue("Person's ID is lower then 0.", personTwo.getId() > 0);
        
        assertNotSame("Person One and Person Two are the same.", personOne, personTwo);    
    }
}
