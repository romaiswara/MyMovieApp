package roma.android.mymovieapp.utils;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.model.network.APIClient;
import roma.android.mymovieapp.model.network.response.MovieResponse;
import roma.android.mymovieapp.model.repository.MovieRepository;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {
    public static int PAGE_SIZE = 20;
    public static int FIRST_PAGE = 1;
    MovieRepository movieRepository;


    int type;
    public MovieDataSource(int type, Application application) {
        this.type = type;
        movieRepository = new MovieRepository(application);
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        Call<MovieResponse> call;
        if (type == 1){
            call = APIClient.getInstance().getApi().getNowPlaying(Constant.API_KEY,FIRST_PAGE);
        } else {
            call = APIClient.getInstance().getApi().getUpcoming(Constant.API_KEY,FIRST_PAGE);
        }
        call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null){
                            callback.onResult(response.body().getResults(), null, FIRST_PAGE + 1);
                            insertMovies(response.body().getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        Call<MovieResponse> call;
        if (type == 1){
            call = APIClient.getInstance().getApi().getNowPlaying(Constant.API_KEY,params.key);
        } else {
            call = APIClient.getInstance().getApi().getUpcoming(Constant.API_KEY,params.key);
        }
        call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null){
                            Integer adjacentKey =  (params.key > 1) ? params.key-1 : 0;
                            callback.onResult(response.body().getResults(), adjacentKey);
                            insertMovies(response.body().getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });

    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        Call<MovieResponse> call;
        if (type == 1){
            call = APIClient.getInstance().getApi().getNowPlaying(Constant.API_KEY,params.key);
        } else {
            call = APIClient.getInstance().getApi().getUpcoming(Constant.API_KEY,params.key);
        }
        call.enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.body() != null){
                            callback.onResult(response.body().getResults(), params.key+1);
                            insertMovies(response.body().getResults());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                    }
                });
    }

    private void insertMovies(List<Movie> movieList){
        for (int i = 0; i < movieList.size() ; i++) {
            Movie movie = movieList.get(i);
            if (type == 1){
                movie.setIdWithType(Constant.NOWPLAYING+movie.getId());
                movie.setType(Constant.NOWPLAYING);
                movie.setIdOriginal(movie.getId());
            } else {
                movie.setIdWithType(Constant.UPCOMING+movie.getId());
                movie.setType(Constant.UPCOMING);
                movie.setIdOriginal(movie.getId());
            }
            movieRepository.insert(movie);
        }
    }
}
