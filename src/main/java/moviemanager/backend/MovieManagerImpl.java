/*
 * Class to manage movies in the database.
 */
package moviemanager.backend;
import common.EntityValidator;
import common.ServiceFailureException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
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
    private EntityValidator validator = new EntityValidator();
    
    public MovieManagerImpl (DataSource dataSource){
        this.jdbc = new JdbcTemplate(dataSource);
    }
    
    /**
     * Parser for Movie.
     */
    private static final RowMapper<Movie> movieMapper = (ResultSet rs, int rowNum) -> {       
        Long id = rs.getLong("id");
        String title = rs.getString("title");
        int year = rs.getInt("movieYear");
        int length = rs.getInt("length");
        String genre = rs.getString("genre");
        
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
        // to validate if incoming paramter movie is valid
        validator.validateMovie(movie);
        if (movie.getId() != null){throw new IllegalArgumentException();}
        
        log.debug("createMovie({})", movie);
        Map<String, Object> pars = new HashMap<>();
        pars.put("title", movie.getTitle());
        pars.put("movieYear", movie.getYear());
        pars.put("genre", movie.getGenre());
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
        // check validity of incoming data
        validator.validateMovie(movie);
        if (movie.getId() == null){throw new IllegalArgumentException();}
        
        log.debug("updateMovie({})", movie);
        int n = jdbc.update("UPDATE Movies SET title= ?, movieYear= ?, genre= ?, length= ? WHERE id= ?", movie.getTitle(), movie.getYear(), movie.getGenre(), movie.getLength(), movie.getId());
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
    public List<Movie> listAllMovies() throws ServiceFailureException {
        log.debug("findAllMovies()");
        return jdbc.query("SELECT * FROM movies", movieMapper);
    }

}
