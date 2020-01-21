package roma.android.mymovieapp.utils;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import roma.android.mymovieapp.model.database.entity.Movie;


public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {
    public MutableLiveData<MovieDataSource> movieDataSourceLiveData = new MutableLiveData<>();
    int type;

    public MovieDataSourceFactory(int type) {
        this.type = type;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource(type);
        movieDataSourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
