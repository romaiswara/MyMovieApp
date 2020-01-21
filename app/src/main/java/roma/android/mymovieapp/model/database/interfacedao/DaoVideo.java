package roma.android.mymovieapp.model.database.interfacedao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roma.android.mymovieapp.model.database.entity.Video;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface DaoVideo {
    @Insert(onConflict = IGNORE)
    void insertVideo(Video video);

    @Query("SELECT * FROM Video")
    LiveData<List<Video>> getAllVideo();

    @Query("SELECT * FROM Video WHERE idMovie = :id LIMIT 1")
    LiveData<List<Video>> getVideoMovie(int id);

    @Query("SELECT * FROM Video WHERE idMovie = :idMovie LIMIT 1")
    Video getVideoByIdMovie(int idMovie);
}
