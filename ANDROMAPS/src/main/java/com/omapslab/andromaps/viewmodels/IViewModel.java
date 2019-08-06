package com.omapslab.andromaps.viewmodels;

import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

/**
 * MVP Core
 * Interface for all the ViewModels in the app. Mostly concerned with View/Activity
 * lifecycle events
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public interface IViewModel {

    void onCreate(Handler handler);

    void onResume();

    void onPause();

    void onDestroy();

    void setCurrentActivity(AppCompatActivity value);
}
