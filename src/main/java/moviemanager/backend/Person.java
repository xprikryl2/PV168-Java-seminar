/*
 * Class representing person in the database (cast, directors, writers,...).
 */
package moviemanager.backend;

import common.Consts;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class representing entity person.
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class Person {
    private static final SimpleDateFormat sdf = new SimpleDateFormat(Consts.TIME_FORMAT);
    
    private Long id;
    private String name;
    private Calendar birth;

    /**
     * Constructor of class Person.
     * @param name String with name of the person.
     * @param birth Date object with date of birth of he person
     */
    public Person(String name, Calendar birth) {
        this.name = name;
        this.birth = birth;
        this.id = null;
    }
    
    public Person(Long id, String name, Calendar birth) {
        this.name = name;
        this.birth = birth;
        this.id = id;
    }
    
    /**
     * Constructor of class Person.
     */
    public Person() {
        this.id = null;
        this.name = "";
        this.birth = null;
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
    public Long getId() {
        return this.id;
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
    
    public void setId (Long id){
        this.id = id;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public String toString (){
        return this.getId() + " - " + this.getName();
    }
}
