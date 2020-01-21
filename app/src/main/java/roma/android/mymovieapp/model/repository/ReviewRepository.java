package roma.android.mymovieapp.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roma.android.mymovieapp.model.database.AppDb;
import roma.android.mymovieapp.model.database.entity.Review;
import roma.android.mymovieapp.model.database.interfacedao.DaoReview;
import roma.android.mymovieapp.model.network.APIClient;
import roma.android.mymovieapp.model.network.response.ReviewResponse;
import roma.android.mymovieapp.utils.Constant;

public class ReviewRepository {
    private DaoReview daoReview;

    public ReviewRepository(Application application) {
        daoReview = AppDb.getInstance(application).daoReview();
    }

    public LiveData<List<Review>> getAllReview(){
        return daoReview.getAllReview();
    }

    public LiveData<List<Review>> getReviewMovie(int id){
        return daoReview.getReviewMovie(id);
    }

    public Review getReviewById (String idReview){
        return daoReview.getReviewById(idReview);
    }


    public void requestVideo(int id){
        APIClient.getInstance().getApi().getReviewMovieById(id, Constant.API_KEY)
                .enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        if (response.isSuccessful()){
                            List<Review> reviewList = response.body().getResults();
                            for (int i = 0; i < reviewList.size() ; i++) {
                                Review review = reviewList.get(i);
                                review.setIdMovie(id);
                                insert(review);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {

                    }
                });
    }

    public void insert(Review review){
        daoReview.insertReview(review);
    }
}
