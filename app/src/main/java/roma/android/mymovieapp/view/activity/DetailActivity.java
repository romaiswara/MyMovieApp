package roma.android.mymovieapp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;
import java.util.Objects;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.ActivityDetailBinding;
import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.utils.Constant;
import roma.android.mymovieapp.utils.Tools;
import roma.android.mymovieapp.view.adapter.MovieDetailAdapter;
import roma.android.mymovieapp.view.adapter.ReviewAdapter;
import roma.android.mymovieapp.viewmodel.MovieViewModel;
import roma.android.mymovieapp.viewmodel.ReviewViewModel;
import roma.android.mymovieapp.viewmodel.VideoViewModel;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    MovieViewModel movieViewModel;
    VideoViewModel videoViewModel;
    ReviewViewModel reviewViewModel;
    Movie movie;
    String  msg;
    boolean add;
    int idMovie;
    String idWithType;
    String keyVideo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);

        Objects.requireNonNull(getSupportActionBar()).hide();
        movieViewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        videoViewModel = new ViewModelProvider(this).get(VideoViewModel.class);
        reviewViewModel = new ViewModelProvider(this).get(ReviewViewModel.class);

        idMovie = getIntent().getIntExtra("idmovie", 0);
        idWithType = getIntent().getStringExtra("id");


        movie = movieViewModel.getMovieById(idMovie);
        binding.setMovie(movie);

        videoViewModel.requestVideo(movie.getId());
        reviewViewModel.requestReview(movie.getId());
        movieViewModel.requestMovieRecommended(movie.getId());
        movieViewModel.requestMovieSimilar(movie.getId());
        checkAvailable();

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground, true);
        fade.excludeTarget(android.R.id.navigationBarBackground, true);

        getWindow().setEnterTransition(fade);
        getWindow().setExitTransition(fade);


        binding.icn.setOnClickListener(view -> {
            checkAvailable();
            showDialog(msg, add);
        });

        binding.imgBanner.setOnClickListener(view -> {
            goTrailer();
        });

        binding.imgPlay.setOnClickListener(view -> {
            goTrailer();
        });

        ReviewAdapter reviewAdapter = new ReviewAdapter(this, new ArrayList<>(), review -> {

        });
        reviewViewModel.getAllReviewMovie(idMovie).observe(this, reviews -> {
            reviewAdapter.updateData(reviews);
            if (reviews.size() > 0){
                binding.tvKetReview.setVisibility(View.GONE);
            } else {
                binding.tvKetReview.setVisibility(View.VISIBLE);
            }
        });
        binding.rvReview.setLayoutManager(new LinearLayoutManager(this));
        binding.rvReview.setAdapter(reviewAdapter);

        MovieDetailAdapter adapterRecom = new MovieDetailAdapter(this, new ArrayList<>(), (movie, imageView) -> {
            goDetail(movie.getId(), imageView);
        });
        movieViewModel.getAllMovieRecomended(idMovie).observe(this, movieList -> {
            adapterRecom.updateData(movieList);
        });
        binding.rvRecomended.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvRecomended.setAdapter(adapterRecom);

        MovieDetailAdapter adapterSimilar = new MovieDetailAdapter(this, new ArrayList<>(), (movie, imageView) -> {
            goDetail(movie.getId(), imageView);
        });
        movieViewModel.getAllMovieSimilar(idMovie).observe(this, movieList -> {
            adapterSimilar.updateData(movieList);
        });
        binding.rvSimilar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        binding.rvSimilar.setAdapter(adapterSimilar);
    }

    private void goTrailer(){
        keyVideo = videoViewModel.getVideoByIdMovie(idMovie).getKey();
        Log.d("halllo", "goTrailer: "+keyVideo);
        if (!keyVideo.isEmpty()) {
            startActivity(new Intent(DetailActivity.this, TrailerActivity.class).putExtra("keyVideo", keyVideo));
//            startActivity(new Intent(DetailActivity.this, TrailerActivity.class));
        }
    }

    private void goDetail(int id, ImageView imageView){
//        startActivity(new Intent(DetailActivity.this, DetailActivity.class).putExtra("idmovie", id), Tools.getOptions(this, imageView).toBundle());
        startActivity(new Intent(DetailActivity.this, DetailActivity.class).putExtra("idmovie", id));
        finish();
    }

    private void checkAvailable(){
        if (movieViewModel.checkFav(movie.getId())) {
            binding.setIcon(R.drawable.ic_favorite_red);
            msg = "Hapus Favorite";
            add = false;
        } else {
            binding.setIcon(R.drawable.ic_favorite_border);
            msg = "Tambah Favorite";
            add = true;
        }
    }

    private void showDialog(String msg, final boolean add){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Konfirmasi")
                .setMessage(msg)
                .setPositiveButton("Iya", (dialogInterface, i) -> {
                    if (add){
                        insertMovieFav();
                        binding.setIcon(R.drawable.ic_favorite_red);
                    } else {
                        deleteMovieFav();
                        binding.setIcon(R.drawable.ic_favorite_border);
                    }
                }).setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create();
        dialog.show();
    }

    private void insertMovieFav(){
        Movie movieFav = movie;
        movieFav.setIdWithType(Constant.FAVORITE+movie.getId());
        movieFav.setType(Constant.FAVORITE);
        movieViewModel.insertMovies(movieFav);
    }

    private void deleteMovieFav(){
        movieViewModel.delete(Constant.FAVORITE+movie.getId());
    }
}
