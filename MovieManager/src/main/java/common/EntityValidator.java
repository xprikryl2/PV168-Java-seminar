/*
 * Class to validate entities from classes Movie and Person.
 * Contains methods to check for illegal arguments, null arguments etc.
 */
package common;

import moviemanager.backend.Movie;
import moviemanager.backend.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class to validate entities from classes Movie and Person.
 * Contains methods to check for illegal arguments, null arguments etc.
 * @author Lukáš Šrom
 * @date 2015 4 2
 */
public class EntityValidator {
    final static Logger log = LoggerFactory.getLogger(EntityValidator.class);
    
    /**
     * Method to validate Movie object. Checks if object reference isn't null, if title isn't blank or null and if given year and length is over 0.
     * Does NOT check movie ID in any way due to ID possibly being null before inserting to database.
     * @param movie Movie object to be validated.
     * @return Boolean true if Movie object is valid.
     */
    public boolean validateMovie (Movie movie){
        if(movie == null){
            log.error("Object Movie in argument is null!");
            throw new IllegalArgumentException ("Movie is null!");}
        
        if(movie.getTitle() == null){
            log.error("Movie title is set to null!");
            throw new IllegalArgumentException ("Movie title is null!");}
        
        if (movie.getTitle().equals("")){
            log.error("Movie title is blank!");
            throw new IllegalArgumentException ("Movie title is blank!");}
        
        if(movie.getYear() < 0){
            log.error("Movie year less then zero!");
            throw new IllegalArgumentException ("Movie year less then zero!");}
        
        if(movie.getLength() < 0){
            log.error("Movie length less then zero!");
            throw new IllegalArgumentException ("Movie length less then zero!");}
        
        return true;
    }
    
    /**
     * Method to validate Person object. Checks if objects reference isn't null and if name isn't null or blank.
     * Does NOT check person ID in any way due to ID possibly being null before inserting to database.
     * @param person Person object to be validated.
     * @return Boolean true if Person object is valid.
     */
    public boolean validatePerson (Person person){
        if (person == null){
            log.error("Object Person in argument is null!");
            throw new IllegalArgumentException("Person is null!");
        }
        
        if (person.getName() == null){
            log.error("Person name is set to null!");
            throw new IllegalArgumentException("Person name is null!");
        }
        
        if (person.getName().equals("")){
            log.error("Person name is blank!");
            throw new IllegalArgumentException("Person name is blank!");
        }
        
        return true;
    }
}
