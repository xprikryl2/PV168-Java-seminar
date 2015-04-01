/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import common.Consts;
import common.DBUtils;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class RelationshipManagerTest {
    RelationshipManagerImpl relationManager;
    MovieManagerImpl movieManager;
    PersonManagerImpl personManager;
    private DataSource dataSource;

    private static final String URL = "jdbc:derby:memory:MovieManagerDtb-test;create=true";
    //private static final String URL = "jdbc:derby://localhost/MovieManagerDtb";
    final static org.slf4j.Logger log = LoggerFactory.getLogger(RelationshipManagerTest.class);
    
    private DataSource prepareDataSource() throws SQLException {
        BasicDataSource ds = new BasicDataSource();
        
        ds.setUrl(URL);
        return ds;
    }
    
    @Before
    public void setUp() throws SQLException {
        dataSource = prepareDataSource();
        DBUtils.executeSqlScript(dataSource, MovieManager.class.getResourceAsStream("/createTables.sql"));
        
        relationManager = new RelationshipManagerImpl(dataSource);
        personManager = new PersonManagerImpl(dataSource);
        movieManager = new MovieManagerImpl(dataSource);
    }
    
    @After
    public void tearDown() throws SQLException {
        DBUtils.executeSqlScript(dataSource, MovieManager.class.getResourceAsStream("/dropTables.sql"));
    }
    
    @Test
    public void addPersonAsCast (){
        log.info("Testing adding person as cast...");
        String personName = "John Doe (personAsCast)";
        String movieTitle = "Cast Movie";
        
        // create movie
        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setYear(2000);
        movie.setLength(120);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        // create person
        Calendar calendar = new GregorianCalendar(1987,Calendar.APRIL,27);
        Person person = new Person(personName, calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        // add to relationship dtb and check it was added
        relationManager.addPersonToRole(person, movie, Consts.CAST);
        assertTrue(relationManager.checkRole(person, movie, Consts.CAST));
        
        // check if person from relationship dtb is the same as person inserted
        List<Person> resultPerson = relationManager.personsOfMovie(movie);
        assertNotNull(resultPerson);
        assertEquals(resultPerson.get(0).getName(), personName);
        assertEquals(resultPerson.get(0), person);
        
        // check if movie from relationship dtb is the same as movie inserted
        List<Movie> resultMovie = relationManager.moviesOfPerson(person);
        assertNotNull(resultMovie);
        assertEquals(resultMovie.get(0).getTitle(), movieTitle);
        assertEquals(resultMovie.get(0), movie);
    }
    
    @Test
    public void addPersonAsDirector (){
        log.info("Testing adding person as director...");
        
        String personName = "John Doe (personAsDirector)";
        String movieTitle = "Director Movie";
        
        // create movie
        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setYear(1990);
        movie.setLength(180);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        //create person
        Calendar calendar = new GregorianCalendar(1995,Calendar.DECEMBER,1);
        Person person = new Person(personName, calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        // add to relationship dtb and check it was added
        relationManager.addPersonToRole(person, movie, Consts.DIRECTOR);
        assertTrue(relationManager.checkRole(person, movie, Consts.DIRECTOR));
        
        // check if person from relationship dtb is the same as person inserted
        List<Person> resultPerson = relationManager.personsOfMovie(movie);
        assertNotNull(resultPerson);
        assertEquals(resultPerson.get(0).getName(), personName);
        assertEquals(resultPerson.get(0), person);
        
        // check if movie from relationship dtb is the same as movie inserted
        List<Movie> resultMovie = relationManager.moviesOfPerson(person);
        assertNotNull(resultMovie);
        assertEquals(resultMovie.get(0).getTitle(), movieTitle);
        assertEquals(resultMovie.get(0), movie);
    }
    
    @Test
    public void addPersonAsWriter (){
        log.info ("Testing adding person as writer...");
        
        String personName = "John Smith (personAsWriter)";
        String movieTitle = "Writer Movie";
        
        //create movie
        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setYear(2010);
        movie.setLength(150);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        //create person
        Calendar calendar = new GregorianCalendar(1985,Calendar.FEBRUARY,19);
        Person person = new Person(personName, calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        // add to relationship dtb and check it was added
        relationManager.addPersonToRole(person, movie, Consts.WRITER);
        assertTrue(relationManager.checkRole(person, movie, Consts.WRITER));
        
        // check if person from relationship dtb is the same as person inserted
        List<Person> resultPerson = relationManager.personsOfMovie(movie);
        assertNotNull(resultPerson);
        assertEquals(resultPerson.get(0).getName(), personName);
        assertEquals(resultPerson.get(0), person);
        
        // check if movie from relationship dtb is the same as movie inserted
        List<Movie> resultMovie = relationManager.moviesOfPerson(person);
        assertNotNull(resultMovie);
        assertEquals(resultMovie.get(0).getTitle(), movieTitle);
        assertEquals(resultMovie.get(0), movie);
    }
    
    @Test
    public void removePersonFromMovie(){
        log.info("Testing removing person from movie...");
        
        String personName = "Jack London (RemoveFromMovie)";
        String movieTitle = "Remove From Movie";
        
        //create movie
        Movie movie = new Movie();
        movie.setTitle(movieTitle);
        movie.setYear(1906);
        movie.setLength(150);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        //create person
        Calendar calendar = new GregorianCalendar(1876,Calendar.JANUARY,12);
        Person person = new Person(personName, calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        // add to relationship dtb and check it was added
        relationManager.addPersonToRole(person, movie, "writer");
        assertTrue(relationManager.checkRole(person, movie, Consts.WRITER));
        
        // remove person from movie relationship
        relationManager.removeRelationship(person, movie);
        
        // check if relationship between given movie and person no longer exist
        assertTrue(relationManager.personsOfMovie(movie).isEmpty());
        assertTrue(relationManager.moviesOfPerson(person).isEmpty());
    }
}
