package roma.android.mymovieapp.model.repository;


import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roma.android.mymovieapp.model.database.AppDb;
import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.model.database.interfacedao.DaoMovie;
import roma.android.mymovieapp.model.network.APIClient;
import roma.android.mymovieapp.model.network.response.MovieResponse;
import roma.android.mymovieapp.utils.Constant;

public class MovieRepository {
    private DaoMovie movieDao;
    private MutableLiveData<List<Movie>> listMovieMutable = new MutableLiveData<>();

    public MovieRepository(Application application){
        movieDao = AppDb.getInstance(application).daoMovie();
    }

    public MutableLiveData<List<Movie>> getListMovieMutable() {
        return listMovieMutable;
    }

    public void requestMovieNowPlaying(){
        APIClient.getInstance().getApi().getNowPlaying(Constant.API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    List<Movie> movieList = response.body().getResults();
                    if (movieList.size()>0){
                        for (int i = 0; i < movieList.size() ; i++) {
                            Movie movie = movieList.get(i);
                            movie.setIdWithType(Constant.NOWPLAYING+movie.getId());
                            movie.setType(Constant.NOWPLAYING);
                            movie.setIdOriginal(movie.getId());
                            insert(movie);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public void requestMovieUpcoming(){
        APIClient.getInstance().getApi().getUpcoming(Constant.API_KEY, 1).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    List<Movie> movieList = response.body().getResults();
                    if (movieList.size()>0){
                        for (int i = 0; i < movieList.size() ; i++) {
                            Movie movie = movieList.get(i);
                            movie.setIdWithType(Constant.UPCOMING+movie.getId());
                            movie.setType(Constant.UPCOMING);
                            movie.setIdOriginal(movie.getId());
                            insert(movie);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public void requestMovieRecommended(int id){
        APIClient.getInstance().getApi().getRecomMovieById(id, Constant.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    List<Movie> movieList = response.body().getResults();
                    if (movieList.size()>0){
                        for (int i = 0; i < movieList.size() ; i++) {
                            Movie movie = movieList.get(i);
                            movie.setIdWithType(Constant.RECOMMENDED+movie.getId());
                            movie.setType(Constant.RECOMMENDED);
                            movie.setIdOriginal(id);
                            insert(movie);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public void requestMovieSimilar(int id){
        APIClient.getInstance().getApi().getSimilarMovieById(id, Constant.API_KEY).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()){
                    List<Movie> movieList = response.body().getResults();
                    if (movieList.size()>0){
                        for (int i = 0; i < movieList.size() ; i++) {
                            Movie movie = movieList.get(i);
                            movie.setIdWithType(Constant.SIMILAR+movie.getId());
                            movie.setType(Constant.SIMILAR);
                            movie.setIdOriginal(id);
                            insert(movie);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });
    }

    public LiveData<List<Movie>> getAllMovieRecomended(int idMovie){
        return movieDao.getAllMovieRecomended(idMovie);
    }

    public LiveData<List<Movie>> getAllMovieSimilar(int idMovie){
        return movieDao.getAllMovieSimilar(idMovie);
    }

    public LiveData<List<Movie>> getAllMovieNowPlaying(){
        return movieDao.getAllMovieNowPlayingTop20();
    }

    public LiveData<List<Movie>> getAllMovieUpcoming(){
        return movieDao.getAllMovieUpcomingTop20();
    }

    public LiveData<List<Movie>> getAllMovieFavorite(){
        return movieDao.getAllMovieFavorite();
    }

    public Movie getMovieById(int id){
        return movieDao.getMovieById(id);
    }

    public Movie getMovieByIdWithType(String id){
        return movieDao.getMovieByIdWithType(id);
    }

    public void insert(Movie movieList) {
        movieDao.insertMovie(movieList);
    }

    public void delete(String id){
        movieDao.deleteMovieById(id);
    }

    public Movie checkFav(int id){
        return movieDao.checkFav(id);
    }

    public DataSource.Factory getMovieFactoryNowPlaying(){
        return movieDao.movieFactoryNowPlaying();
    }

    public DataSource.Factory getMovieFactoryUpcoming(){
        return movieDao.movieFactoryUpcoming();
    }
}
