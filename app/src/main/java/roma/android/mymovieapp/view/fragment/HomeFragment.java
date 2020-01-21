package roma.android.mymovieapp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.FragmentHomeBinding;
import roma.android.mymovieapp.model.database.entity.Movie;
import roma.android.mymovieapp.utils.Tools;
import roma.android.mymovieapp.view.activity.DetailActivity;
import roma.android.mymovieapp.view.adapter.MovieAdapter;
import roma.android.mymovieapp.viewmodel.MovieViewModel;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    MovieAdapter adapterNowPlaying, adapterUpcoming;
    MovieViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.getListMovieNowPlaying().observe(getActivity(), movies -> adapterNowPlaying.updateData(movies));
        viewModel.getListMovieUpcoming().observe(getActivity(), movies -> adapterUpcoming.updateData(movies));
        viewModel.requestMovieNowPlaying();
        viewModel.requestMovieUpcoming();

        adapterNowPlaying = new MovieAdapter(getContext(), new ArrayList<>(), (movie, imageView) -> {
            startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("idmovie", movie.getId()), Tools.getOptions(getActivity(), imageView).toBundle());
        });
        adapterUpcoming = new MovieAdapter(getContext(), new ArrayList<>(), (movie, imageView) -> {
            startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("idmovie", movie.getId()), Tools.getOptions(getActivity(), imageView).toBundle());
        });

        setRecyclerView(binding.rvSedangTayang, adapterNowPlaying);
        setRecyclerView(binding.rvAkanDatang, adapterUpcoming);

        binding.tvSelengkapnyaSedangTayang.setOnClickListener(view1 -> {
            goAllMovie(view, 1);
        });
        binding.tvSelengkapnyaAkanDatang.setOnClickListener(view1 -> {
            goAllMovie(view, 2);
        });
        return view;
    }

    private void goAllMovie(View view, int type){
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_allMovieFragment, bundle);
    }

    private void setRecyclerView(RecyclerView recyclerView, MovieAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}