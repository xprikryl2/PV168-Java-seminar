/*
 * Class representing person in the database (cast, directors, writers,...).
 */
package com.mycompany.moviemanager;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class Person {
    private long id = 0;
    private String firstName = null;
    private String lastName = null;
    private Calendar birth;
    private List<Movie> affiliatedWithMovies;

    /**
     * Constructor of class Person.
     * @param firstName String with first name of the person.
     * @param lastName String with the last name of the person.
     * @param birth Date object with date of birth of he person
     * @param affiliatedWithMovies List<Movie> with all the movies related to this person.
     */
    public Person(String firstName, String lastName, Calendar birth, List<Movie> affiliatedWithMovies) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birth = birth;
        this.affiliatedWithMovies = affiliatedWithMovies;
        this.id = getId();
    }
    
    /**
     * Constructor of class Person.
     */
    public Person() {
        this.id = getId();
        this.firstName = null;
        this.lastName = null;
        this.birth = null;
        this.affiliatedWithMovies = null;
    }

    /**
     * Method to get movies related to person.
     * @return List<Movie> containing movies affiliated to the person.
     */
    public List<Movie> getAffiliatedWithMovies() {
        return affiliatedWithMovies;
    }

    /**
     * Method to get birth of person.
     * @return Date of birth of given person.
     */
    public Calendar getBirth() {
        return birth;
    }

    /**
     * Method to get first name of the person.
     * @return String containing first name of the person.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Method to get ID of the person.
     * @return Integer unique number representing the person.
     */
    public long getId() {
        return id;
    }

    /**
     * Method to get last name of the person.
     * @return String containing last name of the person.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Method to set movies affiliated with the person.
     * @param affiliatedWithMovies List<Movie> containing movies affiliated with person.
     */
    public void setAffiliatedWithMovies(List<Movie> affiliatedWithMovies) {
        this.affiliatedWithMovies = affiliatedWithMovies;
    }

    /**
     * Method to set birth of person.
     * Limit number of possible calls of this method? Or cancel it completely?
     * @param birth Date of birth of person.
     */
    public void setBirth(Calendar birth) {
        this.birth = birth;
    }

    /**
     * Method to set first name of the person.
     * @param firstName String containing first name of the person.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Method to set last name of person.
     * @param lastName String containing last name of person.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public void setId (long id){
        this.id = id;
    }
}
