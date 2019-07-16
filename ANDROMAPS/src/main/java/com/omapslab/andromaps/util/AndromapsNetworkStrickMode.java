package com.omapslab.andromaps.util;

import android.os.StrictMode;

public class AndromapsNetworkStrickMode {

    public static void permitAll() {
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
    }
}
