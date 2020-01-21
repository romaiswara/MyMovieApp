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
import roma.android.mymovieapp.databinding.ItemReviewBinding;
import roma.android.mymovieapp.model.database.entity.Review;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private Context context;
    private List<Review> list;
    listener listener;

    public ReviewAdapter(Context context, List<Review> list, ReviewAdapter.listener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    public void updateData(List<Review> list){
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemReviewBinding movieBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_review, parent, false);
        return new ViewHolder(movieBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final Review review = list.get(position);

        holder.reviewBinding.setReview(review);
        holder.itemView.setOnClickListener(view -> listener.onClick(review));
    }

    @Override
    public int getItemCount() {
        if (list.size() > 5){
            return 5;
        } else {
            return list.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ItemReviewBinding reviewBinding;
        public ViewHolder(@NonNull ItemReviewBinding reviewBinding){
            super(reviewBinding.getRoot());
            this.reviewBinding = reviewBinding;
        }
    }

    public interface listener {
        void onClick(Review review);
    }
}
