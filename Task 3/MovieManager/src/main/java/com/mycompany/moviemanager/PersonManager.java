/*
 * Class to manage persons in the database.
 */
package com.mycompany.moviemanager;

import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public interface PersonManager {
    
    /**
     * Method to add person to the database.
     * @param person Instance of class Person to be added to database.
     */
    public void addPerson (Person person) throws ServiceFailureException;
    
    /**
     * Method to remove Person from database.
     * @param Person Instance of class Person to be removed from database.
     */
    public void removePerson (long personID) throws ServiceFailureException;
    
    /**
     * Method to find person in the database.
     * @param personID ID of person (integer number > 0).
     * @return Person given by it's ID.
     */
    public Person findPerson (long personID) throws ServiceFailureException;
    
    /**
     * Method to update person profile in the database.
     * @param person Data and person to be updated.
     */
    public void updatePerson (Person person) throws ServiceFailureException;
    
    /**
     * Method to list all person in the database.
     * @return List<Person> containing all persons in the database.
     */
    public List<Person> listAll();
}
