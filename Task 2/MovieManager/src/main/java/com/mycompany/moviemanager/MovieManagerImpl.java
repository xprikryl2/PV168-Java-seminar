/*
 * Class to manage movies in the database.
 */
package com.mycompany.moviemanager;
import java.util.List;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class MovieManagerImpl implements MovieManager {

    @Override
    public void createMovie(Movie movie) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Movie getMovie(Long id) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateMovie(Movie movie) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteMovie(Movie movie) throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Movie> findAllMovies() throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
