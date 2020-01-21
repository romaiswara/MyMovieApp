package roma.android.mymovieapp.model.network.response;

import java.util.List;

import roma.android.mymovieapp.model.database.entity.Video;

public class VideoResponse {
    int id;
    List<Video> results;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Video> getResults() {
        return results;
    }

    public void setResults(List<Video> results) {
        this.results = results;
    }
}
