/*
 * Class to manage persons in the database.
 */
package com.mycompany.moviemanager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class PersonManagerImpl implements PersonManager{
    //private final DataSource dataSource;
    final static Logger log = LoggerFactory.getLogger(PersonManagerImpl.class);
    private final JdbcTemplate jdbc;
    
    private static final String LOGIN = "administrator";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
    
    public PersonManagerImpl (DataSource dataSource){
            if (dataSource == null){            
                try {
                    dataSource.getConnection(LOGIN, PASSWORD);
                } catch (SQLException ex) {
                    java.util.logging.Logger.getLogger(PersonManagerImpl.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.jdbc = new JdbcTemplate(dataSource);
            }
            else{
                this.jdbc = new JdbcTemplate(dataSource);
            }      
    }
    
    /**
     *
     */
    public static final RowMapper<Person> personMapper = (rs, rowNum) -> {
        Long id = rs.getLong("id");
        String name = rs.getString("name");
        Calendar cal = Calendar.getInstance();
        try{
        cal.setTime(sdf.parse(rs.getString("birthday")));
        }catch(ParseException ex){
            log.error("Exception when parsing date of birth!" + ex);
        }
        return new Person(id, name, cal);
    };
    
    /**
     * Method to add person to the database.
     * @param person Instance of class Person to be added to database.
     */
    @Override
    public void addPerson (Person person) throws ServiceFailureException{        
        // check validity of incoming data
        if (person == null){throw new IllegalArgumentException ("Person is set to null!");}
        else if (person.getName().equals(null) || person.getName() == ""){throw new IllegalArgumentException ("Name of the person is not set.");}
        
        log.debug("addPerson({})", person);
        Map<String, Object> pars = new HashMap<>();
        pars.put("name", person.getName());
        pars.put("birthday", sdf.format(person.getBirth().getTime()));
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("persons").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();
        System.out.println("ID " + id);
        person.setId(id);
        System.out.println (person.getId());
    }
    
    /**
     * Method to remove Person from database.
     * @param personID ID of Person to be removed from database.
     */
    @Override
    public void removePerson (long personID) throws ServiceFailureException{        
        if (personID < 1){throw new IllegalArgumentException("Person ID lower then 0!");}
        
        log.debug("removePerson({})", personID);
        int n = jdbc.update("DELETE FROM persons WHERE id = ?", personID);
        if (n != 1) throw new ServiceFailureException("Person with ID " + personID + " not deleted");
    }
    
    /**
     * Method to find person in the database.
     * @param personID ID of person (integer number > 0).
     * @return Person given by it's ID.
     */
    @Override
    public Person findPerson (long personID) throws ServiceFailureException{        
        if (personID < 1){throw new IllegalArgumentException("Person ID lower then 1!");}
        
        log.debug("findPerson({})", personID);
        List<Person> list = jdbc.query("SELECT * FROM persons WHERE id= ?", personMapper, personID);
        return list.isEmpty() ? null : list.get(0);
    }
    
    private Person resultSetToPerson (ResultSet rs) throws SQLException{
        Person person = new Person();
        
        person.setId(rs.getLong("id"));
        person.setName(rs.getString("name"));
        //person.setBirth(rs.getObject("birthday", GregorianCalendar.class));
        //person.setAffiliatedWithMovies((List<Movie>) rs.getArray("movies"));
        
        return person;
    }
    
    /**
     * Method to update person profile in the database.
     * @param person Data and person to be updated.
     */
    @Override
    public void updatePerson (Person person) throws ServiceFailureException{
        if (person == null){throw new IllegalArgumentException ("Person pointer is null!");}
        else if (person.getName().equals("")){throw new IllegalArgumentException ("Person name is empty!");}
        
        log.debug("updatePerson({})", person);
            System.out.println (person.getId());
            System.out.println (person.getName());
            System.out.println (sdf.format(person.getBirth().getTime()));
            Person p = findPerson(person.getId());
            System.out.println (p.getId());
        jdbc.update("UPDATE persons SET name=?, birthday=? WHERE id=?", person.getName(), sdf.format(person.getBirth().getTime()).toString());
        //System.out.println ("N is: " + n);
        //if (n != 1) throw new ServiceFailureException("Person " + person + " not updated");
    }
    
    /**
     * Method to list all person in the database.
     * @return List<Person> containing all persons in the database.
     */
    public List<Person> listAll() throws ServiceFailureException{    
        log.debug("listAllPersons()");
        return jdbc.query("SELECT * FROM persons", personMapper);
    }
}
