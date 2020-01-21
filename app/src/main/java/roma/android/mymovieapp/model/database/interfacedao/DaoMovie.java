package roma.android.mymovieapp.model.database.interfacedao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;


import roma.android.mymovieapp.model.database.entity.Movie;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DaoMovie {
    @Insert(onConflict = REPLACE)
    void insertMovie(Movie movie);

    @Query("SELECT * FROM Movie WHERE type LIKE '1'")
    LiveData<List<Movie>> getAllMovieNowPlaying();

    @Query("SELECT * FROM Movie WHERE type LIKE '2'")
    LiveData<List<Movie>> getAllMovieUpcoming();

    @Query("SELECT * FROM Movie WHERE type LIKE '3'")
    LiveData<List<Movie>> getAllMovieFavorite();

    @Query("SELECT * FROM Movie WHERE idWithType LIKE :id")
    Movie getMovieById(String id);

    @Query("DELETE FROM Movie WHERE idWithType LIKE :id")
    void deleteMovieById(String id);

    @Query("SELECT * FROM Movie WHERE id = :id AND type LIKE '3'")
    Movie checkFav(int id);
}
