package roma.android.mymovieapp.model.database.interfacedao;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import roma.android.mymovieapp.model.database.entity.Movie;

import static androidx.room.OnConflictStrategy.IGNORE;
import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoMovie {
    @Insert(onConflict = IGNORE)
    void insertMovie(Movie movie);

    @Insert(onConflict = IGNORE)
    void insertMovies(List<Movie> movie);


    @Query("SELECT * FROM Movie WHERE type LIKE '1'")
    DataSource.Factory<Integer, Movie> movieFactoryNowPlaying();

    @Query("SELECT * FROM Movie WHERE type LIKE '2'")
    DataSource.Factory<Integer, Movie> movieFactoryUpcoming();

    @Query("SELECT * FROM Movie WHERE type LIKE '1' LIMIT 20")
    LiveData<List<Movie>> getAllMovieNowPlayingTop20();

    @Query("SELECT * FROM Movie WHERE type LIKE '2' LIMIT 20")
    LiveData<List<Movie>> getAllMovieUpcomingTop20();

    @Query("SELECT * FROM Movie WHERE type LIKE '1'")
    LiveData<List<Movie>> getAllMovieNowPlaying();

    @Query("SELECT * FROM Movie WHERE type LIKE '2'")
    LiveData<List<Movie>> getAllMovieUpcoming();

    @Query("SELECT * FROM Movie WHERE type LIKE '3'")
    LiveData<List<Movie>> getAllMovieFavorite();

    @Query("SELECT * FROM Movie WHERE type LIKE '4' AND idOriginal = :idMovie")
    LiveData<List<Movie>> getAllMovieRecomended(int idMovie);

    @Query("SELECT * FROM Movie WHERE type LIKE '5' AND idOriginal = :idMovie")
    LiveData<List<Movie>> getAllMovieSimilar(int idMovie);

    @Query("SELECT * FROM Movie WHERE id = :id")
    Movie getMovieById(int id);

    @Query("SELECT * FROM Movie WHERE idWithType LIKE :id")
    Movie getMovieByIdWithType(String id);

    @Query("DELETE FROM Movie WHERE idWithType LIKE :id")
    void deleteMovieById(String id);

    @Query("SELECT * FROM Movie WHERE id = :id AND type LIKE '3'")
    Movie checkFav(int id);
}
