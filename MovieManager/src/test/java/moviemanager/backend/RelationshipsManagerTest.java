/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import common.DBUtils;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class RelationshipsManagerTest {
    RelationshipManagerImpl relationManager;
    MovieManagerImpl movieManager;
    PersonManagerImpl personManager;
    private DataSource dataSource;

    private static final String URL = "jdbc:derby:memory:MovieManagerDtb-test;create=true";
    //private static final String URL = "jdbc:derby://localhost/MovieManagerDtb";
    
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
        System.out.println ("Testing adding person as cast...");
        
        Movie movie = newMovie();
        //movie.setTitle("Cast");
       // movie.setYear(2015);
        //movie.setLength(120);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        Calendar calendar = new GregorianCalendar(1995,11,1);
        Person person = new Person("Jane Doe (personAsCast)", calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        relationManager.addPersonToRole(person, movie, "movieCast");
        assertTrue(relationManager.checkIfPersonIsCast(person, movie));
    }
    
    @Test
    public void addPersonAsDirector (){
        System.out.println ("Testing adding person as director...");
        
        Movie movie = new Movie();
        movie.setTitle("Director");
        movie.setYear(2015);
        movie.setLength(120);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        Calendar calendar = new GregorianCalendar(1995,11,1);
        Person person = new Person("John Doe (personAsDirector)", calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        relationManager.addPersonToRole(person, movie, "director");
        assertTrue(relationManager.checkIfPersonIsDirector(person, movie));
    }
    
    @Test
    public void addPersonAsWriter (){
        System.out.println ("Testing adding person as writer...");
        
        Movie movie = new Movie();
        movie.setTitle("Writer");
        movie.setYear(2015);
        movie.setLength(120);
        movieManager.createMovie(movie);
        assertNotNull(movie);
        
        Calendar calendar = new GregorianCalendar(1995,11,1);
        Person person = new Person("John Smith (personAsWriter)", calendar);
        personManager.createPerson(person);
        assertNotNull(person);
        
        relationManager.addPersonToRole(person, movie, "writer");
        assertTrue(relationManager.checkIfPersonIsWriter(person, movie));
    }
    
    private Movie newMovie() {
        Movie movie = new Movie();
            movie.setTitle("film");
            movie.setYear(2015);
            movie.setLength(120);
    /*      List<String> genreList = new ArrayList<>();
            genre.add("genre"));
                genreList.add("Comedy");
            movie.setGenre(genreList);
                List<Person> personList = new ArrayList<>();
                    Person person = new Person();
                    personList.add(person);
            movie.setDirector(personList);
            movie.setWriter(personList);
            movie.setCast(personList);
    */    return movie;
    }

}
