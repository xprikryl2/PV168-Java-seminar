/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import common.Consts;
import common.ServiceFailureException;
import common.DBUtils;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Šrom
 * @date 2015 3 7
 */
public class PersonManagerTest {
    PersonManagerImpl personManager;
    private DataSource dataSource;

    private static final String URL = "jdbc:derby:memory:MovieManagerDtb-test;create=true";
    //private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb";
    final static org.slf4j.Logger log = LoggerFactory.getLogger(PersonManagerTest.class);
    
    private static DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        //we will use in memory database
        ds.setUrl(URL);
        return ds;
    }
    
    @Before
    public void setUp() throws SQLException {
        dataSource = prepareDataSource();
        DBUtils.executeSqlScript(dataSource, MovieManager.class.getResourceAsStream("/createTables.sql"));
        
        personManager = new PersonManagerImpl(dataSource);
        SimpleDateFormat sdf = new SimpleDateFormat(Consts.TIME_FORMAT);
    }
    
    @After
    public void tearDown() throws SQLException {
        DBUtils.executeSqlScript(dataSource, MovieManager.class.getResourceAsStream("/dropTables.sql"));
    }

    /**
     * Unit test for adding persons to database.
     * Attempts to add person to the database and checks if person is present in list of all persons in the database.
     */
    @Test
    public void testAddPerson() {
        log.info ("Testing adding person...");
        
        Calendar calendar = new GregorianCalendar(1995, Calendar.DECEMBER, 1);        
            
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
        log.info ("Testing removing person...");
        try {
            Calendar calendar = new GregorianCalendar(1987, Calendar.JUNE, 17);            
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
        log.info ("Testing searching for person by ID...");
        try{
            Calendar calendar = new GregorianCalendar(1933, Calendar.JANUARY, 9);
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
        log.info ("Testing updating person...");
        String newName = "James Tiberius Kirk (Update)";
        String personName = "James T. Kirk (Update)";
        
        Calendar calendar = new GregorianCalendar(1956, Calendar.AUGUST, 19);
        Calendar updatedCalendar = new GregorianCalendar(1955, Calendar.SEPTEMBER, 19);
        Person person = new Person(personName, calendar);
        Person updatedPerson = new Person(newName, updatedCalendar);
        
        personManager.createPerson(person);
        Long id = person.getId();
        Person res = personManager.getPerson(id);
        res.setName(newName);
        res.setBirth(calendar);
        res.setId(person.getId());
        personManager.updatePerson(res);
        assertEquals(newName, res.getName());
        assertEquals(res.getBirth(), person.getBirth());
        res.setBirth(updatedCalendar);
        assertTrue(res.getBirth() != person.getBirth());
        assertEquals(res.getBirth(), updatedCalendar);
    }
    
    @Test //(expected = IllegalArgumentException.class)
    public void createPersonWithIncorrectArgument (){
        log.info("Testing creating person with incorrect arguments...");
        
        String personName = "John Doe (createIncorrectly)";
        
        // null argument
        try{
            personManager.createPerson(null);
        }catch(IllegalArgumentException ex){
            log.debug("Creating person with argument null threw " + IllegalArgumentException.class.getCanonicalName() + ".");
        }
        
        Calendar calendar = new GregorianCalendar(1987,Calendar.APRIL,27);
        Person person = new Person(personName, calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        // name set to null
        try{
            person.setName(null);
            personManager.createPerson(person);
        }catch(IllegalArgumentException ex){
            log.debug("Creating person with name null threw " + IllegalArgumentException.class.getCanonicalName() + ".");
        }
        
        // blank name
        try{
            person.setName("");
            personManager.createPerson(person);
        }catch(IllegalArgumentException ex){
            log.debug("Creating person with blank name threw " + IllegalArgumentException.class.getCanonicalName() + ".");
        }
        
    }
    
    private static Comparator<Person> idComparator = new Comparator<Person>() {
        @Override
        public int compare(Person p1, Person p2) {
            return Long.valueOf(p1.getId()).compareTo(Long.valueOf(p2.getId()));
        }
    };
    
}
