package com.omapslab.andromaps.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import androidx.core.content.IntentCompat;

/**
 * Activity Helpers
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 * -------------------------------------------------------------
 */
public class ActivityHelper {

    /**
     * ToActivity
     * @param a
     * @param activity
     */
    public void toActivity(Activity a, Class<?> activity) {
        Intent i = new Intent(a, activity);
        a.startActivity(i);
    }

    /**
     * ToActivity with Action
     * @param a
     * @param activity
     * @param listener
     */
    public void toActivity(Activity a, Class<?> activity, ToActivityListener listener) {
        Intent i = new Intent(a, activity);
        listener.setAction(i);
        a.startActivity(i);
    }

    /**
     * To Activity Callback
     *
     * @param a
     * @param key
     * @param activity
     * @param listener
     */
    public void toActivityCallback(Activity a, int key, Class<?> activity, ToActivityListener listener) {
        Intent i = new Intent(a, activity);
        listener.setAction(i);
        a.startActivityForResult(i, key);
    }

    /**
     * To Activity Callback without listener
     *
     * @param a
     * @param key
     * @param activity
     */
    public void toActivityCallback(Activity a, int key, Class<?> activity) {
        Intent i = new Intent(a, activity);
        a.startActivityForResult(i, key);
    }

    /**
     * ToForceActivity
     * @param a
     * @param activity
     */
    public void toForceActivity(Activity a, Class<?> activity) {
        Intent i = new Intent(a, activity);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        a.startActivity(i);
        a.finish();
    }

    /**
     * System Exit
     * @param a
     */
    @SuppressLint("WrongConstant")
    public void systemExit(Activity a) {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        a.startActivity(i);
    }

    /**
     * ToForceActivity with Action
     * @param a
     * @param activity
     * @param listener
     */
    public void toForceActivity(Activity a, Class<?> activity, ToActivityListener listener) {
        Intent i = new Intent(a, activity);
        listener.setAction(i);
        a.startActivity(i);
        a.finish();
    }

    public interface ToActivityListener {
        void setAction(Intent i);
    }

}
