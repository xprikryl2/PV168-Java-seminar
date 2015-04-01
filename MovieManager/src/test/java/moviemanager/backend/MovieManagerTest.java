/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import common.DBUtils;
import java.sql.SQLException;
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
public class MovieManagerTest {
    MovieManagerImpl manager;
    private DataSource dataSource;

    private static final String URL = "jdbc:derby:memory:MovieManagerDtb-test;create=true";
    
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
        
        manager = new MovieManagerImpl(dataSource);
    }
    
    @After
    public void tearDown() throws SQLException {
        DBUtils.executeSqlScript(dataSource, MovieManager.class.getResourceAsStream("/dropTables.sql"));
    }
    
    @Test
    public void testCreateMovie() {
        Movie movie = newMovie();
        manager.createMovie(movie);
        Long movieId = movie.getId();
        assertNotNull(movieId);
        Movie result = manager.getMovie(movieId);
        assertEquals(movie, result);
        assertNotSame(movie, result);
    }
    
    @Test
    public void testDeleteMovie() {
        Movie movie = newMovie();
        manager.createMovie(movie);
        Long movieId = movie.getId();
        manager.deleteMovie(movieId);
        movie = manager.getMovie(movieId);
        assertNull(movie);
        
    }
    
    @Test
    public void testUpdateMovie() {
        Movie movie = newMovie();
        manager.createMovie(movie);
        
        movie.setTitle("film2");
            movie.setYear(2016);
            movie.setLength(61);
    /*            List<String> genreList = new ArrayList<>();
                genreList.add("Comedy2");
            movie.setGenre(genreList);
                List<Person> personList = new ArrayList<>();
                    Person person = new Person();
                    personList.add(person);
                    personList.add(person);
            movie.setDirector(personList);
                personList.add(person);
            movie.setWriter(personList);
                personList.add(person);
            movie.setCast(personList);
*/        
        
        manager.updateMovie(movie);
        Long movieId = movie.getId();
        assertNotNull(movieId);
        Movie result = manager.getMovie(movieId);
        assertEquals(movie, result);
        assertNotSame(movie, result);
        //assertDeepEquals(movie, result);
    }

    @Test
    public void testMovieWithWrongAttributes() {

        Movie movie;
        
        //try create movie with null parametr
        try {
            manager.createMovie(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        //try create movie with assigned id
        movie = newMovie();
        movie.setId(1l);
        try {
            manager.createMovie(movie);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        //try delete movie with null id
        try {
            movie = newMovie();
            manager.updateMovie(movie);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        //try update movie with null parametr
        try {
            manager.createMovie(null);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        //try update movie with null id
        try {
            movie = newMovie();
            manager.updateMovie(movie);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }

        //try movie with null title
        movie = newMovie();
        try {
            movie.setTitle(null);
            manager.createMovie(movie);
            manager.updateMovie(movie);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        //try movie with year less then zero
        movie = newMovie();
        try {
            movie.setYear(-1);
            manager.createMovie(movie);
            manager.updateMovie(movie);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
        //try movie with length less then zero
        movie = newMovie();
        try {
            movie.setLength(-1);
            manager.createMovie(movie);
            manager.updateMovie(movie);
            fail();
        } catch (IllegalArgumentException ex) {
            //OK
        }
        
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
    
    private void assertDeepEquals(Movie expected, Movie actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getYear(), actual.getYear());
        assertEquals(expected.getGenre(), actual.getGenre());
        assertEquals(expected.getLength(), actual.getLength());
        assertEquals(expected.getDirector(), actual.getDirector());
        assertEquals(expected.getWriter(), actual.getWriter());
        assertEquals(expected.getCast(), actual.getCast());
    }

}
