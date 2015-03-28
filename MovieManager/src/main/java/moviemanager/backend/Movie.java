/*
 * Class movie representing movie instance in the database.
 */
package moviemanager.backend;

import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class Movie {
    private Long id;
    private String title;
    private int year;
    private List<String> genre;
    private int length;
    private List<Person> director;
    private List<Person> writer;
    private List<Person> cast;

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
    public Movie(String title, int year, List<String> genre, int length, List<Person> director, List<Person> writer, List<Person> cast) {
        this.id = null;
        this.title = title;
        this.year = year;
        this.genre = genre;
        this.length = length;
        this.director = director;
        this.writer = writer;
        this.cast = cast;
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
        this.director = null;
        this.writer = null;
        this.cast = null;
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
    public List<String> getGenre() {
        return genre;
    }
    
    /**
     * Method to set genre of the movie.
     * @param genre List<String> containing genres of the movie.
     */
    public void setGenre(List<String> genre) {
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
    
    /**
     * Get method to return List of directors.
     * @return List<Person> containing directors of the movie.
     */
    public List<Person> getDirector() {
        return director;
    }
    
    /**
     * Method to set director of the movie.
     * @param director List<Person> with directors of the movie.
     */
    public void setDirector(List<Person> director) {
        this.director = director;
    }
    
    /**
     * Get method to return List of Writers.
     * @return List<Person> containing writers of the movie.
     */
    public List<Person> getWriter() {
        return writer;
    }
    
    /**
     * Method to set writer of the movie.
     * @param writer List<Person> containing writers of the movie.
     */
    public void setWriter(List<Person> writer) {
        this.writer = writer;
    }
    
    /**
     * Get method to return List of actors.
     * @return List<Person> containing actors in the movie.
     */
    public List<Person> getCast() {
        return cast;
    }
    
    /**
     * Method to set cast of movie.
     * @param cast List<Person> with actors in the movie.
     */
    public void setCast(List<Person> cast) {
        this.cast = cast;
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
    
}
