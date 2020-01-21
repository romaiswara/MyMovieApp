package roma.android.mymovieapp.model.database.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import roma.android.mymovieapp.utils.Constant;
import roma.android.mymovieapp.utils.Tools;

@Entity
public class Movie {
    @PrimaryKey
    @NonNull
    String idWithType;
    int id, idOriginal;
    String popularity, vote_count, poster_path, title, vote_average, overview, release_date,backdrop_path, type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdOriginal() {
        return idOriginal;
    }

    public void setIdOriginal(int idOriginal) {
        this.idOriginal = idOriginal;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getUrlPoster(){
        return Constant.URLIMAGE +poster_path;
    }
    public String getUrlBackdrop(){
        return Constant.URLIMAGE+poster_path;
    }

    @NonNull
    public String getIdWithType() {
        return idWithType;
    }

    public void setIdWithType(@NonNull String idWithType) {
        this.idWithType = idWithType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getReleaseDateFormat(){
        return Tools.changeFormateDate(release_date);
    }
}
