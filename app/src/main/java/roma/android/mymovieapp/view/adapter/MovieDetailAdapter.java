package roma.android.mymovieapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.ItemMovieDetailBinding;
import roma.android.mymovieapp.model.database.entity.Movie;


public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> {
    private Context context;
    private List<Movie> list;
    listener listener;

    public MovieDetailAdapter(Context context, List<Movie> list, MovieDetailAdapter.listener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void updateData(List<Movie> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieDetailBinding movieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie_detail, parent, false);
        return new ViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Movie movie = list.get(position);

        holder.movieBinding.setMovie(movie);
        holder.itemView.setOnClickListener(view -> listener.onClick(movie, holder.movieBinding.imgView));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieDetailBinding movieBinding;
        public ViewHolder(@NonNull ItemMovieDetailBinding movieBinding){
            super(movieBinding.getRoot());
            this.movieBinding = movieBinding;
        }
    }

    public interface listener {
        void onClick(Movie movie, ImageView imageView);
    }
}
