package roma.android.mymovieapp.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;

import roma.android.mymovieapp.R;
import roma.android.mymovieapp.databinding.ActivityTrailerBinding;
import roma.android.mymovieapp.utils.Constant;

public class TrailerActivity extends YouTubeBaseActivity {
    ActivityTrailerBinding binding;
    String keyVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trailer);

        keyVideo = getIntent().getStringExtra("keyVideo");
        Log.d("halllo", "onCreate: "+keyVideo);

        binding.YouTubePlayer.initialize(Constant.API_KEY_YOUTUBE, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(keyVideo);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(TrailerActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
