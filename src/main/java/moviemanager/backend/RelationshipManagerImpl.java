/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package moviemanager.backend;

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
 * @author Lukáš
 */
class RelationshipManagerImpl implements RelationshipManager {
    final static Logger log = LoggerFactory.getLogger(RelationshipManagerImpl.class);
    private final JdbcTemplate jdbc;
    
    public RelationshipManagerImpl(DataSource dataSource){
        this.jdbc = new JdbcTemplate(dataSource);
    }
    
    @Override
    @Transactional
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
        List<Movie> listOfMovies = new ArrayList<>();
        MovieManagerImpl movieManager = new MovieManagerImpl(jdbc.getDataSource());
            for (int i = 0; i < listOfMovieId.size(); i++){
                listOfMovies.add(movieManager.getMovie(listOfMovieId.get(i)));
            }
        return listOfMovies;
    }

    @Override
    public List<Person> personsOfMovie(Movie movie) throws ServiceFailureException {
        log.debug("Persons of movie ({})", movie);
        List<Long> listOfPersonId = jdbc.query("SELECT * FROM relationships WHERE movieId = ?", personIdMapper, movie.getId());
        List<Person> listOfPersons = new ArrayList<>();
        PersonManagerImpl personManager = new PersonManagerImpl(jdbc.getDataSource());
            for (int i = 0; i < listOfPersonId.size(); i++){
                listOfPersons.add(personManager.getPerson(listOfPersonId.get(i)));
            }
        return listOfPersons;
    }
    
    @Override
    @Transactional
    public void removeRelationship(Person person, Movie movie) throws ServiceFailureException {
        log.debug("removing relationship between person and movie", person, movie);
        
        int n = jdbc.update("DELETE FROM relationships WHERE personId = ? AND movieId = ?", person.getId(), movie.getId());
        if (n != 1) throw new ServiceFailureException("Movie affiliation of person with ID " + person.getId() + " not deleted");
    }
    
    public Boolean checkRole(Person person, Movie movie, String role){
        log.debug("checking if person is " + role + " of movie", person, movie);
        
        List<Boolean> list = jdbc.query("SELECT " + role + " FROM relationships WHERE personId = ? AND movieId = ?", affiliationMapper, person.getId(), movie.getId());
        return list.get(0);
    }
    
    private static final RowMapper<Boolean> affiliationMapper = (ResultSet rs, int rowNum) -> {
        boolean isAffiliated = rs.getBoolean(1);
        return isAffiliated;
    };
    
    private static final RowMapper<Long> movieIdMapper = (ResultSet rs, int rowNum) -> {
        Long id = rs.getLong("movieId");
        return id;
    };
    
    private static final RowMapper<Long> personIdMapper = (ResultSet rs, int rowNum) -> {
        Long id = rs.getLong("personId");
        return id;
    };
}
