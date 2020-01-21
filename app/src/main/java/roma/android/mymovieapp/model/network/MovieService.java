package roma.android.mymovieapp.model.network;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.model.network.response.MovieResponse;

public interface MovieService {
    @GET("now_playing")
    Call<MovieResponse> getNowPlaying(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("upcoming")
    Call<MovieResponse> getUpcoming(
            @Query("api_key") String apiKey,
            @Query("page") int page
    );

    @GET("{Id}")
    Call<Movie> getMovieById(
            @Path("Id") String id,
            @Query("api_key") String apiKey
    );
}
