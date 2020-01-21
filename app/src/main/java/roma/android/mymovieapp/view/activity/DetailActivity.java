package roma.android.mymovieapp.view.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import java.util.Objects;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.ActivityDetailBinding;
import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.utils.Constant;
import roma.android.mymovieapp.viewmodel.MovieViewModel;

public class DetailActivity extends AppCompatActivity {
    ActivityDetailBinding binding;
    MovieViewModel viewModel;
    Movie movie;
    String  msg;
    boolean add;
    boolean internetAcces;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        Objects.requireNonNull(getSupportActionBar()).hide();
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        internetAcces = getIntent().getBooleanExtra("access", false);
        id = getIntent().getIntExtra("id", 0);

        if (internetAcces){
            viewModel.getListMovieMutable().observe(this, movies -> binding.setMovie(movies.get(0)));
            viewModel.getMovieByIdOnline(String.valueOf(id));
            binding.cvFav.setVisibility(View.GONE);
        } else {
            movie = viewModel.getMovieById(getIntent().getStringExtra("id"));
            binding.setMovie(movie);
            checkAvailable();
            binding.cvFav.setVisibility(View.VISIBLE);
        }

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
    }

    private void checkAvailable(){
        if (viewModel.checkFav(movie.getId())) {
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
                .setPositiveButton("Iya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (add){
                            insertMovieFav();
                            binding.setIcon(R.drawable.ic_favorite_red);
                        } else {
                            deleteMovieFav();
                            binding.setIcon(R.drawable.ic_favorite_border);
                        }
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
        viewModel.insertMovies(movieFav);
    }

    private void deleteMovieFav(){
        viewModel.delete(Constant.FAVORITE+movie.getId());
    }
}
