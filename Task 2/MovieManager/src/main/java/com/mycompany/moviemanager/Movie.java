/*
 * Class movie representing movie instance in the database.
 */
package com.mycompany.moviemanager;

import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 3 4
 */
public class Movie {
    private int idMovie = 0;
    private String movieTitle = null;
    private int year = 0;
    private List<String> genre;
    private List<Person> director;
    private List<Person> writer;
    private List<Person> cast;
    private int length = 0;

    /**
     * Movie class constructor.
     * @param movieTitle is title of the movie as string.
     * @param year year in which movie was produced.
     * @param length is integer length of movie in minutes.
     * @param genre is genre of the movie.
     * @param director is List of names (represented by class Person) of movie directors.
     * @param writer is List of names (represented by class Person) of movie writers.
     * @param cast  is List of names (represented by class Person) of movies cast.
     */
    public Movie(String movieTitle, int year, int length, List<String> genre, List<Person> director, List<Person> writer, List<Person> cast) {
        this.movieTitle = movieTitle;
        this.year = year;
        this.length = length;
        this.genre = genre;
        this.director = director;
        this.writer = writer;
        this.cast = cast;
    }

    /**
     * Get method to return List of actors.
     * @return List<Person> containing actors in the movie.
     */
    public List<Person> getCast() {
        return cast;
    }

    /**
     * Get method to return List of directors.
     * @return List<Person> containing directors of the movie.
     */
    public List<Person> getDirector() {
        return director;
    }

    /**
     * Get method to return genre of the movie.
     * @return List<String> containing genres of the movie.
     */
    public List<String> getGenre() {
        return genre;
    }
    
    /**
     * Get method to return ID of the movie.
     * @return Integer number representing artificial unique ID of movie.
     */
    public int getIdMovie() {
        return idMovie;
    }

    /**
     * Get method to return length of movie in minutes.
     * @return Integer number of minutes.
     */
    public int getLength() {
        return length;
    }

    /**
     * Get method to return movie title as string.
     * @return String containing movie title.
     */
    public String getMovieTitle() {
        return movieTitle;
    }

    /**
     * Get method to return List of Writers.
     * @return List<Person> containing writers of the movie.
     */
    public List<Person> getWriter() {
        return writer;
    }

    /**
     * Get method to return year in which movie was produced.
     * @return Integer number of the year.
     */
    public int getYear() {
        return year;
    }

    /**
     * Method to set cast of movie.
     * @param cast List<Person> with actors in the movie.
     */
    public void setCast(List<Person> cast) {
        this.cast = cast;
    }

    /**
     * Method to set director of the movie.
     * @param director List<Person> with directors of the movie.
     */
    public void setDirector(List<Person> director) {
        this.director = director;
    }

    /**
     * Method to set genre of the movie.
     * @param genre List<String> containing genres of the movie.
     */
    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    /**
     * Method to automatically set ID of the movie. 
     */
    private void setIdMovie() {
        //TODO: add generation of unique ID
        // simply counting movies or somethimg more cool (like hash of movie title?
        this.idMovie = idMovie;
    }

    /**
     * Method to set length of movie in minutes.
     * @param length Integer number of minutes.
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * Method to set title of the movie.
     * @param movieTitle String containing title of the movie.
     */
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    /**
     * Method to set writer of the movie.
     * @param writer List<Person> containing writers of the movie.
     */
    public void setWriter(List<Person> writer) {
        this.writer = writer;
    }

    /**
     * Method to set year of the movie.
     * @param year Integer number representing year in which movie was produced.
     */
    public void setYear(int year) {
        this.year = year;
    }
    
}
