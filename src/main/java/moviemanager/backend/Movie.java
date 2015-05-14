/*
 * Class movie representing movie instance in the database.
 */
package moviemanager.backend;

import java.util.List;

/**
 * Class representing entity movie.
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class Movie {
    private Long id;
    private String title;
    private int year;
    private String genre;
    private int length;

    /**
     * Movie class constructor.
     * @param title is title of the movie as string.
     * @param year year in which movie was produced.
     * @param genre is genre of the movie.
     * @param length is integer length of movie in minutes.
     * @param director is List of names (represented by class Person) of movie directors.
     * @param writer is List of names (represented by class Person) of movie writers.
     * @param cast  is List of names (represented by class Person) of movies cast.
     */
    public Movie(String title, int year, String genre, int length, List<Person> director, List<Person> writer, List<Person> cast) {
        this.id = null;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.length = length;
    }

    /**
     * Movie class constructor.
     */
    public Movie() {
        this.id = null;
        this.title = null;
        this.year = 0;
        this.genre = null;
        this.length = 0;
    }
    
    /**
     * Get method to return id of the movie.
     * @return Integer number representing artificial unique ID of movie.
     */
    public Long getId (){
        return id;
    }
    
    /**
     * Method to set id of the movie.
     * @param id Integer number representing artificial unique ID of movie.
     */
    public void setId (Long id){
        this.id = id;
    }
    
    /**
     * Get method to return movie title as string.
     * @return String containing movie title.
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * Method to set title of the movie.
     * @param title String containing title of the movie.
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * Get method to return year in which movie was produced.
     * @return Integer number of the year.
     */
    public int getYear() {
        return year;
    }
    
    /**
     * Method to set year of the movie.
     * @param year Integer number representing year in which movie was produced.
     */
    public void setYear(int year) {
        this.year = year;
    }
    
    /**
     * Get method to return genre of the movie.
     * @return List<String> containing genres of the movie.
     */
    public String getGenre() {
        return genre;
    }
    
    /**
     * Method to set genre of the movie.
     * @param genre List<String> containing genres of the movie.
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    /**
     * Get method to return length of movie in minutes.
     * @return Integer number of minutes.
     */
    public int getLength() {
        return length;
    }
    
    /**
     * Method to set length of movie in minutes.
     * @param length Integer number of minutes.
     */
    public void setLength(int length) {
        this.length = length;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movie other = (Movie) obj;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        if(this.id != null) {
            return this.id.hashCode();
        } else {
            return 0;
        }
    }    
    
    @Override
    public String toString(){
        return this.getId() + " - " + this.getTitle();
    }
}
