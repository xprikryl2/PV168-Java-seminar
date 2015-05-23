/*
 * Class to manage relationships between movies and persons (person affilidated with movies and vice versa).
 */
package moviemanager.backend;

import common.ServiceFailureException;
import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public interface RelationshipManager {
    /**
     * Method to add given person to movie as actor.
     * @param person Person to be added as cast.
     * @param movie Movie to which person will be added.
     */
    public void addPersonToRole (Person person, Movie movie, String role) throws ServiceFailureException;
    
    /**
     * Method to find all movies connected to given person.
     * @param person Person who's movies will be searched for.
     */
    public List<Movie> moviesOfPerson (Person person) throws ServiceFailureException;
    
    /**
     * Method to find all persons connected to the movie.
     * @param movie Movie to which all related persons will be found.
     */
    public List<Person> personsOfMovie (Movie movie) throws ServiceFailureException;
    
    /**
     * Method to remove relation between given person and given movie.
     * @param person Person to be removed from movie relation.
     * @param movie Movie to be removed from person relation.
     */
    public void removeRelationship (Person person, Movie movie, String role) throws ServiceFailureException;
}
