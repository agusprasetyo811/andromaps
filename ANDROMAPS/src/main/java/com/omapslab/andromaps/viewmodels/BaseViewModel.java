package com.omapslab.andromaps.viewmodels;

import android.os.Handler;

/**
 * MVP Core
 * Base class for all ViewModels.
 * Biggest reason for this class is that all viewModels need a reference
 * to the UI Thread.
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public abstract class BaseViewModel {

    protected Handler mUiThreadHandler;

    public void onCreate(Handler handler) {
        mUiThreadHandler = handler;
    }

    public void onDestroy() {
        mUiThreadHandler = null;
    }
}
