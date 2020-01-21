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

    public void getMovieNowPlaying(){
        repository.getMovieNowPlaying();
    }

    public void getMovieUpcoming(){
        repository.getMovieUpcoming();
    }

    public void getMovieByIdOnline(String id){
        repository.getMovieByIdOnline(id);
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

    public Movie getMovieById(String id){
        return repository.getMovieById(id);
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
