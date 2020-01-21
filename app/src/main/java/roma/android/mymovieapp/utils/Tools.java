package roma.android.mymovieapp.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Tools {

    public static ActivityOptionsCompat getOptions(Activity activity, ImageView imageView) {
        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, imageView, ViewCompat.getTransitionName(imageView));
    }

    public static String changeFormateDate(String date) {
        final String OLD_FORMAT = "yyyy-MM-dd";
        final String NEW_FORMAT = "dd MMM yyyy";

        String oldDateString = date;
        String newDateString;

        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        Date d = null;
        try {
            d = sdf.parse(oldDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern(NEW_FORMAT);
        newDateString = sdf.format(d);
        return newDateString;
    }

    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager connectivityManager
                    = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        } catch (Exception e) {
            return false;
        }
    }

    public static void showDialogNoConnection(Context context){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Pemberitahuan!")
                .setMessage("Tidak Ada Koneksi Internet")
                .setPositiveButton("Kembali", (dialogInterface, i) -> dialogInterface.dismiss())
                .setCancelable(false)
                .create();
        dialog.show();
    }
}
