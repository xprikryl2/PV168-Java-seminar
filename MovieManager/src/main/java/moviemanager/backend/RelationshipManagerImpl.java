/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

import common.ServiceFailureException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author Lukáš
 */
class RelationshipManagerImpl implements RelationshipManager {
    final static Logger log = LoggerFactory.getLogger(PersonManagerImpl.class);
    private final JdbcTemplate jdbc;
    
    private static final String CAST = "movieCast";
    private static final String DIRECTOR = "director";
    private static final String WRITER = "writer";
    
    public RelationshipManagerImpl(DataSource dataSource){
        this.jdbc = new JdbcTemplate(dataSource);
    }
    
    @Override
    public void addPersonToRole (Person person, Movie movie, String role){
        log.debug("addAs" + role + "{}", person);
        Map<String, Object> pars = new HashMap<>();
        pars.put("movieId", movie.getId());
        pars.put("personId", person.getId());
        pars.put(role, true);
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("relationships").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();
    }
    
    @Override
    public List<Movie> moviesOfPerson(Person person) throws ServiceFailureException {
        log.debug("Movies of person ({})", person);
        List<Long> listOfMovieId = jdbc.query("SELECT * FROM relationships WHERE personId = ?", movieIdMapper, person.getId());
        List<Movie> listOfMovies = null;
        MovieManagerImpl movieManager = new MovieManagerImpl(jdbc.getDataSource());
            for (int i = 0; i < listOfMovieId.size(); i++){
                listOfMovies.add(movieManager.getMovie(listOfMovieId.get(i)));
            }
        return listOfMovies;
    }
    
    private static final RowMapper<Long> movieIdMapper = (ResultSet rs, int rowNum) -> {
        Long id = rs.getLong("movieId");
        return id;
    };
    
    private static final RowMapper<Long> personIdMapper = (ResultSet rs, int rowNum) -> {
        Long id = rs.getLong("personId");
        return id;
    };

    @Override
    public List<Person> personsOfMovie(Movie movie) throws ServiceFailureException {
        log.debug("Persons of movie ({})", movie);
        List<Long> listOfPersonId = jdbc.query("SELECT * FROM relationships WHERE movieId = ?", personIdMapper, movie.getId());
        List<Person> listOfPersons = null;
        PersonManagerImpl personManager = new PersonManagerImpl(jdbc.getDataSource());
            for (int i = 0; i < listOfPersonId.size(); i++){
                listOfPersons.add(personManager.getPerson(listOfPersonId.get(i)));
            }
        return listOfPersons;
    }

    @Override
    public void removePersonFromMovie(Person person, Movie movie) throws ServiceFailureException {
        log.debug("removing person from movie", person, movie);
        
        int n = jdbc.update("DELETE FROM relationships WHERE personId = ? AND movieId = ?", person.getId(), movie.getId());
        if (n != 1) throw new ServiceFailureException("Movie affiliation of person with ID " + person.getId() + " not deleted");
    }
    
    @Override
    public void removeMovieFromPerson(Person person, Movie movie) throws ServiceFailureException {
        log.debug("removing movie from person", movie, person);
        
        int n = jdbc.update("DELETE FROM relationships WHERE movieId = ? AND personId = ?", movie.getId(), person.getId());
        if (n != 1) throw new ServiceFailureException("Movie affiliation of person with ID " + person.getId() + " not deleted");
    }
}
