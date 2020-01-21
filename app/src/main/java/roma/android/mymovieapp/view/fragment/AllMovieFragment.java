package roma.android.mymovieapp.view.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.FragmentAllMovieBinding;
import roma.android.mymovieapp.utils.Tools;
import roma.android.mymovieapp.view.activity.DetailActivity;
import roma.android.mymovieapp.view.adapter.MovieAllAdapter;
import roma.android.mymovieapp.viewmodel.MovieAllViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllMovieFragment extends Fragment {
    FragmentAllMovieBinding binding;
    MovieAllViewModel viewModel;
    MovieAllAdapter adapter;


    public AllMovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_all_movie, container, false);
        View view = binding.getRoot();

        Bundle bundle = getArguments();
        int type = bundle.getInt("type", 0);

        adapter = new MovieAllAdapter(getContext(), (movie, imageView) -> {
            if (!Tools.isOnline(getContext())){
                Tools.showDialogNoConnection(getContext());
            } else {
                startActivity(new Intent(getActivity(), DetailActivity.class).putExtra("id", movie.getId()).putExtra("access", true), Tools.getOptions(getActivity(), imageView).toBundle());
            }
        });
        binding.rvList.setLayoutManager(new LinearLayoutManager(getContext()));

        viewModel = new ViewModelProvider(this).get(MovieAllViewModel.class);
        viewModel.getMovie(type);
        viewModel.moviePagedList.observe(getActivity(), movies -> adapter.submitList(movies));

        binding.rvList.setAdapter(adapter);

        return view;
    }



}
