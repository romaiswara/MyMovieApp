package roma.android.mymovieapp.model.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import roma.android.mymovieapp.model.database.AppDb;
import roma.android.mymovieapp.model.database.entity.Video;
import roma.android.mymovieapp.model.database.interfacedao.DaoVideo;
import roma.android.mymovieapp.model.network.APIClient;
import roma.android.mymovieapp.model.network.response.VideoResponse;
import roma.android.mymovieapp.utils.Constant;

public class VideoRepository {
    private DaoVideo daoVideo;

    public VideoRepository(Application application){
        daoVideo = AppDb.getInstance(application).daoVideo();
    }

    public Video getVideoByIdMovie(int id){
        return daoVideo.getVideoByIdMovie(id);
    }

    public LiveData<List<Video>> getLiveDataMovie() {
        return daoVideo.getAllVideo();
    }

    public LiveData<List<Video>> getVideoMovie(int id) {
        return daoVideo.getVideoMovie(id);
    }

    public void requestVideo(int id){
        APIClient.getInstance().getApi().getVideoMovieById(id, Constant.API_KEY)
                .enqueue(new Callback<VideoResponse>() {
                    @Override
                    public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                        if (response.isSuccessful()){
                            List<Video> videoList = response.body().getResults();
                            if (videoList.size()>0){
                                for (int i = 0; i < videoList.size() ; i++) {
                                    Video video = videoList.get(i);
                                    // hanya insert 1 video trailer dari youtube tiap movie
                                    if (video.getSite().equals(Constant.YOUTUBE) && video.getType().equals(Constant.TRAILER)){
                                        video.setIdMovie(id);
                                        insert(video);
                                    }
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<VideoResponse> call, Throwable t) {

                    }
                });
    }

    public void insert(Video video){
        daoVideo.insertVideo(video);
    }
}
