package roma.android.mymovieapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import java.util.ArrayList;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.FragmentFavoriteBinding;
import roma.android.mymovieapp.view.activity.DetailActivity;
import roma.android.mymovieapp.view.adapter.MovieFavAdapter;
import roma.android.mymovieapp.viewmodel.MovieViewModel;

public class FavoriteFragment extends Fragment {
    FragmentFavoriteBinding binding;
    MovieViewModel viewModel;
    MovieFavAdapter adapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.getListMovieFavorite().observe(getActivity(), movies -> adapter.updateData(movies));

        binding.rvFav.setHasFixedSize(true);
        binding.rvFav.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new MovieFavAdapter(getContext(), new ArrayList<>(), (movie, imageView) -> {
            Intent intent = new Intent(getActivity(), DetailActivity.class).putExtra("idmovie", movie.getId());
            ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(), imageView, ViewCompat.getTransitionName(imageView));
            startActivity(intent, options.toBundle());
        });
        binding.rvFav.setAdapter(adapter);
        return view;
    }
}