package com.omapslab.andromaps.networking;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    final String message;
    private Context mContext;

    public ConnectivityInterceptor(String message, Context context) {
        this.message = message;
        mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!isOnline(mContext)) {
            throw new NoConnectivityException(message);
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
        return (netInfo != null && netInfo.isConnected());
    }
}

class NoConnectivityException extends IOException {

    final String message;

    NoConnectivityException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}

