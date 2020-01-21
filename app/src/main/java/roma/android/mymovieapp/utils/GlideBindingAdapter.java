package roma.android.mymovieapp.utils;

import android.content.Context;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import roma.android.mymovieapp.R;

public class GlideBindingAdapter {

    @BindingAdapter("imageResource")
    public static void setImageResource(ImageView view, int imageUrl){
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image);
        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }


    @BindingAdapter("imageUrl")
    public static void setImageResource(ImageView view, String imageUrl){
        Context context = view.getContext();
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image);
        Glide.with(context)
                .setDefaultRequestOptions(options)
                .load(imageUrl)
                .into(view);
    }
}
