package roma.android.mymovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.utils.MovieDataSource;
import roma.android.mymovieapp.utils.MovieDataSourceFactory;


public class MovieAllViewModel extends ViewModel {

    public LiveData<PagedList<Movie>> moviePagedList;
    private LiveData<MovieDataSource> movieDataSourceLiveData;

    public MovieAllViewModel() {
    }

    public void getMovie(int type){
        MovieDataSourceFactory movieDataSourceFactory = new MovieDataSourceFactory(type);
        movieDataSourceLiveData = movieDataSourceFactory.movieDataSourceLiveData;
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(MovieDataSource.PAGE_SIZE)
                .build();
        moviePagedList = new LivePagedListBuilder<>(movieDataSourceFactory, config).build();
    }


}
