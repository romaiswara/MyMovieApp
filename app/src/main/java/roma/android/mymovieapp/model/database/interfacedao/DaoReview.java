package roma.android.mymovieapp.model.database.interfacedao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import roma.android.mymovieapp.model.database.entity.Review;

import static androidx.room.OnConflictStrategy.IGNORE;

@Dao
public interface DaoReview {
    @Insert(onConflict = IGNORE)
    void insertReview(Review review);

    @Query("SELECT * FROM Review")
    LiveData<List<Review>> getAllReview();

    @Query("SELECT * FROM Review WHERE idMovie = :id")
    LiveData<List<Review>> getReviewMovie(int id);

    @Query("SELECT * FROM Review WHERE id = :idReview LIMIT 1")
    Review getReviewById(String idReview);

}
