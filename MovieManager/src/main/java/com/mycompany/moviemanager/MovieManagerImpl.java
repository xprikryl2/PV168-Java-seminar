/*
 * Class to manage movies in the database.
 */
package com.mycompany.moviemanager;
import static com.mycompany.moviemanager.PersonManagerImpl.log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class MovieManagerImpl implements MovieManager {
    private final JdbcTemplate jdbc;
    final static Logger log = LoggerFactory.getLogger(MovieManagerImpl.class);
    
    private static final String LOGIN = "administrator";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    
    public MovieManagerImpl (DataSource dataSource){
        this.jdbc = new JdbcTemplate(dataSource);
    }
    
    /**
     *
     */
    private static final RowMapper<Movie> movieMapper = (ResultSet rs, int rowNum) -> {       
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        int year = rs.getInt("movieYear");
        int length = rs.getInt("length");
        List<String> genre = new ArrayList<>();
        genre.add(rs.getString("genre"));
        
        Movie movie = new Movie();
        movie.setId(id);
        movie.setTitle(title);
        movie.setYear(year);
        movie.setLength(length);
        movie.setGenre(genre);
        
        return movie;
    };
    
    @Override
    @Transactional
    public void createMovie(Movie movie) throws ServiceFailureException{

        // checkvalidity of incoming data
        if(movie == null){throw new IllegalArgumentException ("Movie is null!");}
        if(movie.getId() != null){throw new IllegalArgumentException ("Movie id is not null!");}
        if(movie.getTitle() == null){throw new IllegalArgumentException ("Movie title is null!");}
        if(movie.getYear() < 0){throw new IllegalArgumentException ("Movie year less then zero!");}
        if(movie.getLength() < 0){throw new IllegalArgumentException ("Movie length less then zero!");}
        
        log.debug("createMovie({})", movie);
        Map<String, Object> pars = new HashMap<>();
        pars.put("title", movie.getTitle());
        pars.put("movieYear", movie.getYear());
        pars.put("length", movie.getLength());
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("movies").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();
        movie.setId(id);
    }

    @Override
    public Movie getMovie(Long id) throws ServiceFailureException{        
        if (id < 1){throw new IllegalArgumentException("Movie id is lower then 1!");}
        
        log.debug("getMovie({})", id);
        List<Movie> list = jdbc.query("SELECT * FROM movies WHERE id= ?", movieMapper, id);
        return list.isEmpty() ? null : list.get(0);
    }

    @Override
    @Transactional
    public void updateMovie(Movie movie) throws ServiceFailureException {

        // checkvalidity of incoming data
        if(movie == null){throw new IllegalArgumentException ("Movie is null!");}
        if(movie.getId() == null){throw new IllegalArgumentException ("Movie id is null!");}
        if(movie.getTitle() == null){throw new IllegalArgumentException ("Movie title is null!");}
        if(movie.getYear() < 0){throw new IllegalArgumentException ("Movie year less then zero!");}
        if(movie.getLength() < 0){throw new IllegalArgumentException ("Movie length less then zero!");}
        
        log.debug("updateMovie({})", movie);
        jdbc.update("UPDATE MOVIES SET title=?, movieYear=?, genre=?, length=? WHERE id=?", movie.getTitle(), movie.getYear(), movie.getGenre(), movie.getLength());
    }

    @Override
    @Transactional
    public void deleteMovie(Long id) throws ServiceFailureException {        
        if (id < 1){throw new IllegalArgumentException("Person ID lower then 1!");}
        
        log.debug("deleteMovie({})", id);
        int n = jdbc.update("DELETE FROM movies WHERE id = ?", id);
        if (n != 1) throw new ServiceFailureException("Movie with ID " + id + " not deleted");
    }

    @Override
    public List<Movie> findAllMovies() throws ServiceFailureException {
        log.debug("findAllMovies()");
        return jdbc.query("SELECT * FROM movies", movieMapper);
    }

}
