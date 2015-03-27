/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.moviemanager;
import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public interface MovieManager {
    
    /**
     * Stores new movie into database. Id for the new movie is automatically
     * generated and stored into id attribute.
     * 
     * @param movie movie to be created.
     * @throws IllegalArgumentException when movie is null, or movie has already 
     * assigned id.
     * @throws  ServiceFailureException when db operation fails.
     */
    void createMovie(Movie movie) throws ServiceFailureException;
    
    /**
     * Returns movie with given id.
     * 
     * @param id primary key of requested movie.
     * @return movie with given id or null if such movie does not exist.
     * @throws IllegalArgumentException when given id is null.
     * @throws  ServiceFailureException when db operation fails.
     */
    Movie getMovie(Long id) throws ServiceFailureException;
    
    /**
     * Updates movie in database.
     * 
     * @param movie updated movie to be stored into database.
     * @throws IllegalArgumentException when movie is null, or movie has null id.
     * @throws  ServiceFailureException when db operation fails.
     */
    void updateMovie(Movie movie) throws ServiceFailureException;
    
    /**
     * Deletes movie from database. 
     * 
     * @param id primary key of movie to be deleted from db.
     * @throws IllegalArgumentException when movie is null, or movie has null id.
     * @throws  ServiceFailureException when db operation fails.
     */
    void deleteMovie(Long id) throws ServiceFailureException;
    
    /**
     * Returns list of all movies in the database.
     * 
     * @return list of all movies in database.
     * @throws  ServiceFailureException when db operation fails.
     */
    List<Movie> listAllMovies() throws ServiceFailureException;
    
}
