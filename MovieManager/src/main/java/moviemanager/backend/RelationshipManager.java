/*
 * Class to manage relationships between movies and persons (person affilidated with movies and vice versa).
 */
package moviemanager.backend;

import common.ServiceFailureException;

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
    public void addPersonAsCast (Person person, Movie movie) throws ServiceFailureException;
    
    /**
     * Method to add given person as director.
     * @param person Person to be added as director.
     * @param movie Movie to which person will be added.
     */
    public void addPersonAsDirector (Person person, Movie movie) throws ServiceFailureException;
    
    /**
     * Method to add person to movie as writer.
     * @param person Person to be added as writer.
     * @param movie Movie to which person will be added.
     */
    public void addPersonAsWriter (Person person, Movie movie) throws ServiceFailureException;
    
    /**
     * Method to find all movies connected to given person.
     * @param person Person who's movies will be searched for.
     */
    public void moviesOfPerson (Person person) throws ServiceFailureException;
    
    /**
     * Method to find all persons connected to the movie.
     * @param movie Movie to which all related persons will be found.
     */
    public void personsOfMovie (Movie movie) throws ServiceFailureException;
    
    /**
     * Method to remove relation between given person and given movie.
     * @param person Person to be removed from movie relation.
     * @param movie Movie to be removed from person relation.
     */
    public void removePersonfromMovie (Person person, Movie movie) throws ServiceFailureException;
}
