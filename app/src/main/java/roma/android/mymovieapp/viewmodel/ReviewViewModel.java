package roma.android.mymovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roma.android.mymovieapp.model.database.entity.Review;
import roma.android.mymovieapp.model.repository.ReviewRepository;

public class ReviewViewModel extends AndroidViewModel {
    ReviewRepository repository;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        repository = new ReviewRepository(application);
    }
    public LiveData<List<Review>> getAllReview(){
        return repository.getAllReview();
    }

    public LiveData<List<Review>> getAllReviewMovie(int id){
        return repository.getReviewMovie(id);
    }

    public Review getReviewById (String idReview){
        return repository.getReviewById(idReview);
    }

    public void requestReview(int id){
        repository.requestVideo(id);
    }
}
