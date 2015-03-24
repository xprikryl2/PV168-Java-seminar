/*
 * Class to manage persons in the database.
 */
package com.mycompany.moviemanager;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class PersonManagerImpl {
    private final DataSource dataSource;
    final static Logger log = LoggerFactory.getLogger(PersonManagerImpl.class);
    
    private static final String LOGIN = "administrator";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    
    public PersonManagerImpl (DataSource dataSource){
        if (dataSource == null){
            log.info("No DataSource received in constructor. Using default values.");
            BasicDataSource bsd = new BasicDataSource();
            bsd.setUrl(URL);
        }
        this.dataSource = dataSource;
    }
    
    /**
     * Method to add person to the database.
     * @param person Instance of class Person to be added to database.
     */
    public ResultSet addPerson (Person person) throws ServiceFailureException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");
        
        // checkvalidity of incoming data
        if (person == null){throw new IllegalArgumentException ("Person is set to null!");}
        else if (person.getName().equals(null) || person.getName() == ""){throw new IllegalArgumentException ("Name of the person is not set.");}
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            // try to store data in dtb, if error occures, dtb will come back to it's original state
            try(PreparedStatement st = conn.prepareStatement("INSERT INTO PERSONS (name, birthday) VALUES (?,?)", Statement.RETURN_GENERATED_KEYS)){
                //st.setInt(1, getId());                
                st.setString(1, person.getName());
                st.setString(2, sdf.format(person.getBirth().getTime()));  //TODO: how to store calendar type in dtb?
                //st.setArray(3, (Array) person.getAffiliatedWithMovies());   // TODO: check if this works and it's viable
                // checks if only one row was updated (meaning only one entry to table as made)
                if(st.executeUpdate() != 1){
                    log.error("More rows inserted when trying to insert new person!");
                    throw new ServiceFailureException ("More rows inserted when trying to insert new person: " + person);
                }
                ResultSet keys = st.getGeneratedKeys();
                return keys;
            }catch (SQLException ex){   // in case error occures when trying to store data
                log.error("Cannot store data to dtb!", ex);
                conn.rollback();        // atomically fail and restore all changes made in this session
            }
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when adding person!", ex);
        };
        return null;
    }
    
    /**
     * Method to remove Person from database.
     * @param Person Instance of class Person to be removed from database.
     */
    public void removePerson (long personID) throws ServiceFailureException{        
        if (personID < 1){throw new IllegalArgumentException("Person ID lower then 0!");}
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement("DELETE FROM PERSONS WHERE id=?")){
                st.setLong(1, personID);
                if(st.executeUpdate()!=1) {
                    log.error("Removing person with id " + personID + " failed!");
                    throw new ServiceFailureException("Person with id " + personID + " was not deleted!");
                }
            }catch(SQLException ex){    // in case error occures when trying to remove data
                log.error("Cannot remove data from dtb!", ex);
                conn.rollback();        // atomically fail and restore all changes made in this session
            }        
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when removing person!", ex);
        }; 
    }
    
    /**
     * Method to find person in the database.
     * @param personID ID of person (integer number > 0).
     * @return Person given by it's ID.
     */
    public Person findPerson (long personID) throws ServiceFailureException{        
        if (personID < 1){throw new IllegalArgumentException("Person ID lower then 0!");}
            
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement("SELECT id, name, birthday FROM PERSONS WHERE id=?")){
                st.setLong(1, personID);
                ResultSet rs = st.executeQuery();
                
                if (rs.next()){
                    Person person = resultSetToPerson(rs);
                    if (rs.next()){
                        log.error("Found multiple entities with the same ID " + personID + " in the dtb!");
                        throw new ServiceFailureException ("More entities with the same ID " + personID + " found!");
                    }
                    return person;
                }
                else{
                    return null;
                }
            }catch(SQLException ex){
                log.error("Cannot find data in dtb!", ex);
                conn.rollback();
            }
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when searching for person!", ex);
        };
        return null;
    }
    
    private Person resultSetToPerson (ResultSet rs) throws SQLException{
        Person person = new Person();
        
        person.setId(rs.getLong("id"));
        person.setName(rs.getString("name"));
        person.setBirth(rs.getObject("birthday", GregorianCalendar.class));
        person.setAffiliatedWithMovies((List<Movie>) rs.getArray("movies"));
        
        return person;
    }
    
    /**
     * Method to update person profile in the database.
     * @param person Data and person to be updated.
     */
    public void updatePerson (Person person) throws ServiceFailureException{
        if (person == null){throw new IllegalArgumentException ("Person pointer is null!");}
        else if (person.getName().equals(null) || person.getName() == ""){throw new IllegalArgumentException ("Person name is empty!");}
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement("UPDATE persons SET name=?, birthday=?, movies=? WHERE id=?")){
                st.setString(1, person.getName());
                st.setString(2, person.getBirth().toString());  // TODO
                st.setArray(3, (Array)person.getAffiliatedWithMovies());    // TODO
                
                if (st.executeUpdate() != 1){
                    log.error("Updating more entities then supposed to!");
                    throw new ServiceFailureException("Updating entities failed!");
                }
            }catch(SQLException ex){
                log.error("Cannot update data in the dtb!");
                throw new ServiceFailureException ("Error when trying to update entity in the dtb.", ex);
            }            
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when updating person!", ex);
        };
    }
    
    /**
     * Method to list all person in the database.
     * @return List<Person> containing all persons in the database.
     */
    public List<Person> listAll() throws ServiceFailureException{        
        try(Connection conn = dataSource.getConnection()){
            try (PreparedStatement st = conn.prepareStatement("SELECT id,name FROM PERSONS")) {
                ResultSet rs = st.executeQuery();
                List<Person> result = new ArrayList<>();
                while (rs.next()) {
                    result.add(resultSetToPerson(rs));
                }
                return result;
            }catch(SQLException ex){
                log.error("Cannot lookup data in the dtb!");
                throw new ServiceFailureException ("Error when trying to lookup all entities in the dtb.", ex);
            }
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when listing persons!", ex);
        }        
    }
    
    /**
     * Method to create ID for person. Looks to dtb for highest id and returns found value plus 1.
     * @return ID for the person to be inserted to dtb.
     */
    private int getId(){
        return 6;
    }
}
