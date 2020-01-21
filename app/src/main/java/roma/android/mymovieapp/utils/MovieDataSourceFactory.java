package roma.android.mymovieapp.utils;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import roma.android.mymovieapp.model.database.entity.Movie;


public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {
    public MutableLiveData<MovieDataSource> movieDataSourceLiveData = new MutableLiveData<>();
    int type;
    Application application;

    public MovieDataSourceFactory(int type, Application application) {
        this.type = type;
        this.application = application;
    }

    @Override
    public DataSource<Integer, Movie> create() {
        MovieDataSource movieDataSource = new MovieDataSource(type, application);
        movieDataSourceLiveData.postValue(movieDataSource);
        return movieDataSource;
    }
}
