package com.omapslab.andromaps.helpers;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.content.IntentCompat;

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
        try {

        } catch (Exception e) {}
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
        try {
            Intent i = new Intent(a, activity);
            listener.setAction(i);
            a.startActivity(i);
        } catch (Exception e) {}
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
        try {
            Intent i = new Intent(a, activity);
            listener.setAction(i);
            a.startActivityForResult(i, key);
        } catch (Exception e) {}
    }

    /**
     * To Activity Callback without listener
     *
     * @param a
     * @param key
     * @param activity
     */
    public void toActivityCallback(Activity a, int key, Class<?> activity) {
        try {
            Intent i = new Intent(a, activity);
            a.startActivityForResult(i, key);
        } catch (Exception e) {}
    }

    /**
     * ToForceActivity
     * @param a
     * @param activity
     */
    public void toForceActivity(Activity a, Class<?> activity) {
        try {
            Intent i = new Intent(a, activity);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            a.startActivity(i);
            a.finish();
        } catch (Exception e) {}
    }

    /**
     * System Exit
     * @param a
     */
    @SuppressLint("WrongConstant")
    public void systemExit(Activity a) {
        try {
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS | Intent.FLAG_ACTIVITY_NO_ANIMATION);
            a.startActivity(i);
        } catch (Exception e) {}
    }

    /**
     * ToForceActivity with Action
     * @param a
     * @param activity
     * @param listener
     */
    public void toForceActivity(Activity a, Class<?> activity, ToActivityListener listener) {
        try {
            Intent i = new Intent(a, activity);
            listener.setAction(i);
            a.startActivity(i);
            a.finish();
        } catch (Exception e) {}
    }

    public interface ToActivityListener {
        void setAction(Intent i);
    }

}
