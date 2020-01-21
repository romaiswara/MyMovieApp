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
import roma.android.mymovieapp.databinding.ItemMovieBinding;
import roma.android.mymovieapp.model.database.entity.Movie;


public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private Context context;
    private List<Movie> list;
    listener listener;

    public MovieAdapter(Context context, List<Movie> list, MovieAdapter.listener listener) {
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
        ItemMovieBinding movieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_movie, parent, false);
        return new ViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Movie movie = list.get(position);

        holder.movieBinding.setMovie(movie);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(movie, holder.movieBinding.imgView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMovieBinding movieBinding;
        public ViewHolder(@NonNull ItemMovieBinding movieBinding){
            super(movieBinding.getRoot());
            this.movieBinding = movieBinding;
        }
    }

    public interface listener {
        void onClick(Movie movie, ImageView imageView);
    }
}
