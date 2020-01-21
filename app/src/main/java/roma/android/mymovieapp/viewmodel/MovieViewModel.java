package roma.android.mymovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.model.repository.MovieRepository;


public class MovieViewModel extends AndroidViewModel {
    private MovieRepository repository;
    private MutableLiveData<List<Movie>> listMovieMutable;

    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        listMovieMutable = repository.getListMovieMutable();
    }

    public MutableLiveData<List<Movie>> getListMovieMutable() {
        return listMovieMutable;
    }

    public void requestMovieNowPlaying(){
        repository.requestMovieNowPlaying();
    }

    public void requestMovieUpcoming(){
        repository.requestMovieUpcoming();
    }

    public void requestMovieRecommended(int id){
        repository.requestMovieRecommended(id);
    }

    public void requestMovieSimilar(int id){
        repository.requestMovieSimilar(id);
    }

    public LiveData<List<Movie>> getListMovieNowPlaying(){
        return repository.getAllMovieNowPlaying();
    }

    public LiveData<List<Movie>> getListMovieUpcoming(){
        return repository.getAllMovieUpcoming();
    }

    public LiveData<List<Movie>> getListMovieFavorite(){
        return repository.getAllMovieFavorite();
    }


    public LiveData<List<Movie>> getAllMovieRecomended(int idMovie){
        return repository.getAllMovieRecomended(idMovie);
    }

    public LiveData<List<Movie>> getAllMovieSimilar(int idMovie){
        return repository.getAllMovieSimilar(idMovie);
    }

    public Movie getMovieById(int id){
        return repository.getMovieById(id);
    }

    public Movie getMovieByIdWithType(String idWithType){
        return repository.getMovieByIdWithType(idWithType);
    }

    public void insertMovies(Movie movie){
        repository.insert(movie);
    }

    public void delete(String id){
        repository.delete(id);
    }

    public boolean checkFav(int id){
        if (repository.checkFav(id) != null){
            return true;
        } else {
            return false;
        }
    }
}
