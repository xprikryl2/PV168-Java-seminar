/*
 * Class to manage persons in the database.
 */
package moviemanager.backend;

import common.ServiceFailureException;
import java.sql.ResultSet;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
 * @date 2015 3 27
 */
public class PersonManagerImpl implements PersonManager{
    final static Logger log = LoggerFactory.getLogger(PersonManagerImpl.class);
    private final JdbcTemplate jdbc;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
    
    public PersonManagerImpl (DataSource dataSource){
        this.jdbc = new JdbcTemplate(dataSource);
    }
    
    /**
     * Parser for Person.
     */
    private static final RowMapper<Person> personMapper = (ResultSet rs, int rowNum) -> {
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
    @Transactional
    public void createPerson (Person person) throws ServiceFailureException{        
        // check validity of incoming data
        if (person == null){throw new IllegalArgumentException ("Person is set to null!");}
        else if (person.getName().equals(null) || person.getName() == ""){throw new IllegalArgumentException ("Name of the person is not set.");}
        
        log.debug("addPerson({})", person);
        Map<String, Object> pars = new HashMap<>();
        pars.put("name", person.getName());
        pars.put("birthday", sdf.format(person.getBirth().getTime()));
        
        Long id = (Long)new SimpleJdbcInsert(jdbc).withTableName("persons").usingGeneratedKeyColumns("id").executeAndReturnKey(pars).longValue();
        person.setId(id);
    }
    
    /**
     * Method to remove Person from database.
     * @param personID ID of Person to be removed from database.
     */
    @Override
    @Transactional
    public void deletePerson (long personID) throws ServiceFailureException{        
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
    public Person getPerson (long personID) throws ServiceFailureException{        
        if (personID < 1){throw new IllegalArgumentException("Person ID lower then 1!");}
        
        log.debug("findPerson({})", personID);
        List<Person> list = jdbc.query("SELECT * FROM persons WHERE id= ?", personMapper, personID);
        return list.isEmpty() ? null : list.get(0);
    }
    
    /**
     * Method to update person profile in the database.
     * @param person Data and person to be updated.
     */
    @Override
    @Transactional
    public void updatePerson (Person person) throws ServiceFailureException{
        if (person == null){throw new IllegalArgumentException ("Person pointer is null!");}
        else if (person.getName().equals("")){throw new IllegalArgumentException ("Person name is empty!");}
        
        log.debug("updatePerson({})", person);
            System.out.println (person.getId());
            System.out.println (person.getName());
            System.out.println (sdf.format(person.getBirth().getTime()));
            Person p = getPerson(person.getId());
            System.out.println (p.getId());
        jdbc.update("UPDATE persons SET name=?, birthday=? WHERE id=?", person.getName(), sdf.format(person.getBirth().getTime()));
        //System.out.println ("N is: " + n);
        //if (n != 1) throw new ServiceFailureException("Person " + person + " not updated");
    }
    
    /**
     * Method to list all person in the database.
     * @return List<Person> containing all persons in the database.
     */
    @Override
    public List<Person> listAllPersons() throws ServiceFailureException{    
        log.debug("listAllPersons()");
        return jdbc.query("SELECT * FROM persons", personMapper);
    }
}
