package roma.android.mymovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.model.repository.MovieRepository;
import roma.android.mymovieapp.utils.MovieDataSource;
import roma.android.mymovieapp.utils.MovieDataSourceFactory;


public class MovieAllViewModel extends AndroidViewModel {

    public LiveData<PagedList<Movie>> moviePagedList;
    private LiveData<MovieDataSource> movieDataSourceLiveData;
    public LiveData<PagedList<Movie>> movieDbList;

    MovieRepository repository;

    Application application;

    public MovieAllViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        repository = new MovieRepository(application);
    }

    public void getMovie(int type){
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(type, application);
        movieDataSourceLiveData = movieDataSourceFactory.movieDataSourceLiveData;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        moviePagedList = new LivePagedListBuilder<>(movieDataSourceFactory, config).build();

        if (type == 1){
            movieDbList = new LivePagedListBuilder<>(repository.getMovieFactoryNowPlaying(), /* page size */ 20).build();
        } else {
            movieDbList = new LivePagedListBuilder<>(repository.getMovieFactoryUpcoming(), /* page size */ 20).build();
        }
    }


}
