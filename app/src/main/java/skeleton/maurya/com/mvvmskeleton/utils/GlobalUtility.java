package skeleton.maurya.com.mvvmskeleton.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.util.Random;

import skeleton.maurya.com.mvvmskeleton.BuildConfig;
import skeleton.maurya.com.mvvmskeleton.di.AppInjector;

/**
 * Created by jeetendrapal on 12/9/18.
 */
public class GlobalUtility {

    private static ProgressDialog mProgressDialog;
    private static Bitmap bitmap;

    /***
     * Hide SoftInput Keyboard
     * @param view
     */
    public static void hideKeyboard(View view) {
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null)
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }


    }

    /**
     * Check internet connectivity available or not
     *
     * @param context
     * @return
     */
    public static boolean isNetworkAvailable(Context context) {
        if (context == null)
            return false;

        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connMgr.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected();
    }

    /**
     * @param sizeOfRandomString length of random string
     * @return generate a random string
     */
    public static String getRandomString(final int sizeOfRandomString) {
        String ALLOWED_CHARACTERS = "0123456789qwertyuiopasdfghjklzxcvbnm";
        final Random random = new Random();
        final StringBuilder sb = new StringBuilder(sizeOfRandomString);
        for (int i = 0; i < sizeOfRandomString; ++i)
            sb.append(ALLOWED_CHARACTERS.charAt(random.nextInt(ALLOWED_CHARACTERS.length())));
        return sb.toString();
    }

    /**
     * Shows a progress dialog whenever and wherever required.
     *
     * @param show    - true/false to show/hide the progress dialog
     * @param message - message to be displayed on progress. Default is Please wait..
     */
    public static void showProgress(boolean show, String message) {
        if (show) {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(AppInjector.getActivity());
                mProgressDialog.setMessage(message);
                mProgressDialog.setCancelable(false);
//                mProgressDialog = ProgressDialogs.getProgressDialog(getActivity(), message);
            }
            mProgressDialog.show();

        } else {
            if (mProgressDialog != null) {
                mProgressDialog.dismiss();
            }
        }
    }


    /***
     *convert string image url to bitmap
     * @param name of image
     * @return bitmap image
     */
    public static Bitmap getBitmapFromImage(String name) {
        String imageUrl = BuildConfig.BASE_URL + "image/name/" + name;
        Glide.with(AppInjector.getActivity()).asBitmap()
                .load(imageUrl)
                .into(new SimpleTarget<Bitmap>(100, 100) {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        bitmap = resource;
                    }
                });
        return bitmap;
    }


}
