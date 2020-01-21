package roma.android.mymovieapp.model.network;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import roma.android.mymovieapp.utils.Constant;

public class APIClient {
    private static volatile APIClient instance = null;
    private Retrofit retrofit;

    public APIClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    public static APIClient getInstance() {
        if (instance == null) {
            synchronized (APIClient.class){
                instance = new APIClient();
            }
        }
        return instance;
    }

    public MovieService getApi(){
        return retrofit.create(MovieService.class);
    }



}
