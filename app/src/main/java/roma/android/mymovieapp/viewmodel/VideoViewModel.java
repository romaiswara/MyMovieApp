package roma.android.mymovieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import roma.android.mymovieapp.model.database.entity.Video;
import roma.android.mymovieapp.model.repository.VideoRepository;

public class VideoViewModel extends AndroidViewModel {
    VideoRepository repository;

    public VideoViewModel(@NonNull Application application) {
        super(application);
        repository = new VideoRepository(application);
    }

    public LiveData<List<Video>> getVideoMovie(int id){
        return repository.getVideoMovie(id);
    }

    public Video getVideoByIdMovie(int id){
        return repository.getVideoByIdMovie(id);
    }

    public void requestVideo(int id){
        repository.requestVideo(id);
    }
}
