/*
 * Class to manage movies in the database.
 */
package com.mycompany.moviemanager;
import static com.mycompany.moviemanager.PersonManagerImpl.log;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Šrom
 * @author Jakub Mlčák
 * @date 2015 4 3
 */
public class MovieManagerImpl implements MovieManager {
    private final DataSource dataSource;
    final static Logger log = LoggerFactory.getLogger(MovieManagerImpl.class);
    
    private static final String LOGIN = "administrator";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:derby://localhost:1527/MovieManagerDtb;";
    private static final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    
    public MovieManagerImpl (DataSource dataSource){
        if (dataSource == null){
            log.info("No DataSource received in constructor. Using default values.");
            BasicDataSource bsd = new BasicDataSource();
            bsd.setUrl(URL);
        }
        this.dataSource = dataSource;
    }
    
    @Override
    public void createMovie(Movie movie) throws ServiceFailureException{

        // checkvalidity of incoming data
        if(movie == null){throw new IllegalArgumentException ("Movie is null!");}
        if(movie.getId() != null){throw new IllegalArgumentException ("Movie id is not null!");}
        if(movie.getTitle() == null){throw new IllegalArgumentException ("Movie title is null!");}
        if(movie.getYear() < 0){throw new IllegalArgumentException ("Movie year less then zero!");}
        if(movie.getLength() < 0){throw new IllegalArgumentException ("Movie length less then zero!");}
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            // try to store data in dtb, if error occures, dtb will come back to it's original state
            try(PreparedStatement st = conn.prepareStatement("INSERT INTO MOVIES (title, movieYear, genre, length) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS)){
             
                st.setString(1, movie.getTitle());
                st.setInt(2, movie.getYear());
                st.setString(3, "genre");
                //st.setString(3, movie.getGenre());  //TODO convert List<String> to String ??
                st.setInt(4, movie.getLength());

                // checks if only one row was updated (meaning only one entry to table as made)
                if(st.executeUpdate() != 1){
                    log.error("More rows inserted when trying to insert new movie!");
                    throw new ServiceFailureException ("More rows inserted when trying to insert new movie: " + movie);
                }
                ResultSet keys = st.getGeneratedKeys();
                keys.next();
                movie.setId(keys.getLong(1));
                
            }catch (SQLException ex){   // in case error occures when trying to store data
                log.error("Cannot store data to dtb!", ex);
                conn.rollback();        // atomically fail and restore all changes made in this session
            }
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when adding movie!", ex);
        };
    }

    @Override
    public Movie getMovie(Long id) throws ServiceFailureException{
        
        if (id == null){throw new IllegalArgumentException("Person ID is null!");}
        if (id < 1){throw new IllegalArgumentException("Movie id is lower then 1!");}
        
        Movie movie = null;
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement("SELECT id, title, movieYear, genre, length FROM MOVIES WHERE id=?")){
                st.setLong(1, id);
                ResultSet rs = st.executeQuery();
                
                if (rs.next()){
                    movie = resultSetToMovie(rs);                   
                }
            }catch(SQLException ex){
                log.error("Cannot find data in dtb!", ex);
                conn.rollback();
            }
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when getting movie!", ex);
        };
        return movie;
    }
    
    private Movie resultSetToMovie (ResultSet rs) throws SQLException{
        Movie movie = new Movie();
        
        movie.setId(rs.getLong("id"));
        movie.setTitle(rs.getString("title"));
        movie.setYear(rs.getInt("movieYear"));
        //movie.setGenre(rs.getString("genre"));  //TODO convert String to List<String> ??
        movie.setLength(rs.getInt("length"));
        
        return movie;
    }

    @Override
    public void updateMovie(Movie movie) throws ServiceFailureException {

        // checkvalidity of incoming data
        if(movie == null){throw new IllegalArgumentException ("Movie is null!");}
        if(movie.getId() == null){throw new IllegalArgumentException ("Movie id is null!");}
        if(movie.getTitle() == null){throw new IllegalArgumentException ("Movie title is null!");}
        if(movie.getYear() < 0){throw new IllegalArgumentException ("Movie year less then zero!");}
        if(movie.getLength() < 0){throw new IllegalArgumentException ("Movie length less then zero!");}
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            // try to store data in dtb, if error occures, dtb will come back to it's original state
            try(PreparedStatement st = conn.prepareStatement("UPDATE MOVIES SET title=?, movieYear=?, genre=?, length=? WHERE id=?", Statement.RETURN_GENERATED_KEYS)){
             
                st.setString(1, movie.getTitle());
                st.setInt(2, movie.getYear());
                st.setString(3, "genre");
                //st.setString(3, movie.getGenre());  //TODO convert List<String> to String ??
                st.setInt(4, movie.getLength());
                st.setLong(5, movie.getId());
                
                // checks if only one row was updated (meaning only one entry to table as made)
                if(st.executeUpdate() != 1){
                    log.error("Updating more entities then supposed to!");
                    throw new ServiceFailureException ("Updating entities failed!");
                }
                
            }catch (SQLException ex){   // in case error occures when trying to store data
                log.error("Cannot store data to dtb!", ex);
                conn.rollback();        // atomically fail and restore all changes made in this session
            }
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when adding movie!", ex);
        }
    }

    @Override
    public void deleteMovie(Long id) throws ServiceFailureException {
        
        if (id == null){throw new IllegalArgumentException("Person ID is null!");}
        if (id < 1){throw new IllegalArgumentException("Person ID lower then 1!");}
        
        // try to connect to dtb, if not possible or when it's done, session will be automatically terminated
        try(Connection conn = dataSource.getConnection()){
            try(PreparedStatement st = conn.prepareStatement("DELETE FROM MOVIES WHERE id=?")){
                st.setLong(1, id);
                if(st.executeUpdate()!=1) {
                    log.error("Removing movie with id " + id + " failed!");
                    throw new ServiceFailureException("Movie with id " + id + " was not deleted!");
                }
            }catch(SQLException ex){    // in case error occures when trying to remove data
                log.error("Cannot remove data from dtb!", ex);
                conn.rollback();        // atomically fail and restore all changes made in this session
            }        
        }catch (SQLException ex){
            log.error("Database connection problems!", ex);
            throw new ServiceFailureException("Error when removing movie!", ex);
        }
    }

    @Override
    public List<Movie> findAllMovies() throws ServiceFailureException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
