package roma.android.mymovieapp.view.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.ItemMovieSeeMoreBinding;
import roma.android.mymovieapp.model.database.entity.Movie;

public class MovieAllAdapter extends PagedListAdapter<Movie, MovieAllAdapter.ViewHolder> {
    Context context;
    listener listener;

    public MovieAllAdapter(Context context, listener listener) {
        super(DIFF_CALLBACK);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMovieSeeMoreBinding movieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie_see_more, parent, false);
        return new ViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Movie movie = getItem(position);
        if (movie != null) {
            holder.movieBinding.setMovie(movie);
            final ImageView imageView = holder.movieBinding.imgView;
            holder.itemView.setOnClickListener(view -> listener.onClick(movie, imageView));
        }

    }

    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(Movie oldItem, Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieSeeMoreBinding movieBinding;
        public ViewHolder(@NonNull ItemMovieSeeMoreBinding movieBinding){
            super(movieBinding.getRoot());
            this.movieBinding = movieBinding;
        }
    }

    public interface listener {
        void onClick(Movie movie, ImageView imageView);
    }
}
