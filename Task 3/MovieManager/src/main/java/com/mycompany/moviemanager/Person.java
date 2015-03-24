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
    private String name;
    private Calendar birth;
    private List<Movie> affiliatedWithMovies;

    /**
     * Constructor of class Person.
     * @param name String with name of the person.
     * @param birth Date object with date of birth of he person
     * @param affiliatedWithMovies List<Movie> with all the movies related to this person.
     */
    public Person(String name, Calendar birth, List<Movie> affiliatedWithMovies) {
        this.name = name;
        this.birth = birth;
        this.affiliatedWithMovies = affiliatedWithMovies;
        this.id = getId();
    }
    
    /**
     * Constructor of class Person.
     */
    public Person() {
        this.id = getId();
        this.name = "";
        this.birth = null;
        this.affiliatedWithMovies = null;
    }

    /**
     * Method to get movies related to person.
     * @return List<Movie> containing movies affiliated to the person.
     */
    public List<Movie> getAffiliatedWithMovies() {
        return this.affiliatedWithMovies;
    }

    /**
     * Method to get birth of person.
     * @return Date of birth of given person.
     */
    public Calendar getBirth() {
        return this.birth;
    }

    /**
     * Method to get first name of the person.
     * @return String containing first name of the person.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Method to get ID of the person.
     * @return Integer unique number representing the person.
     */
    public long getId() {
        return this.id;
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
     * @param name String containing name of the person.
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void setId (long id){
        this.id = id;
    }
}