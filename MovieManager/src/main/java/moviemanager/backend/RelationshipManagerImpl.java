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
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author Lukáš
 */
class RelationshipManagerImpl implements RelationshipManager {
    final static Logger log = LoggerFactory.getLogger(PersonManagerImpl.class);
    private final JdbcTemplate jdbc;
    
    public RelationshipManagerImpl(DataSource dataSource){
        this.jdbc = new JdbcTemplate(dataSource);
    }

    @Override
    public void addPersonAsCast(Person person, Movie movie) throws ServiceFailureException {
        log.debug("addAsCast{}", person);
        Map<String, Object> pars = new HashMap<>();
        pars.put("movieId", movie.getId());
        pars.put("personId", person.getId());
        pars.put("movieCast", true);
        pars.put("director", checkDirector(person, movie));
        pars.put("writer", checkWriter(person, movie));
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("relationships").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();        
    }

    @Override
    public void addPersonAsDirector(Person person, Movie movie) throws ServiceFailureException {
        log.debug("addAsDirector{}", person);
        Map<String, Object> pars = new HashMap<>();
        pars.put("movieId", movie.getId());
        pars.put("personId", person.getId());
        pars.put("movieCast", checkCast(person, movie));
        pars.put("director", true);
        pars.put("writer", checkWriter(person, movie));
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("relationships").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();
    }

    @Override
    public void addPersonAsWriter(Person person, Movie movie) throws ServiceFailureException {
        log.debug("addAsWriter{}", person);
        Map<String, Object> pars = new HashMap<>();
        pars.put("movieId", movie.getId());
        pars.put("personId", person.getId());
        pars.put("movieCast", checkCast(person, movie));
        pars.put("director", checkDirector(person, movie));
        pars.put("writer", true);
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("relationships").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();
    }

    @Override
    public void moviesOfPerson(Person person) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.        
    }

    @Override
    public void personsOfMovie(Movie movie) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removePersonfromMovie(Person person, Movie movie) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private boolean checkDirector (Person person, Movie movie){
        log.debug("checkPersonAsDirector {}", person);
        
        return false;
    }
    
    private boolean checkWriter (Person person, Movie movie){
        log.debug("checkPersonAsWriter {}", person);
        
        return false;
    }
    
    private boolean checkCast (Person person, Movie movie){
        log.debug("checkPersonAsCast {}", person);
        
        return false;
    }
}
