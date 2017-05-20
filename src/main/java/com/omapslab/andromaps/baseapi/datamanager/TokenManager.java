package com.omapslab.andromaps.baseapi.datamanager;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.omapslab.andromaps.baseapi.model.AccessToken;
import com.omapslab.andromaps.contants.APPS_CORE;

/**
 * Created by omapslab on 11/21/16.
 */

public class TokenManager {


    private SharedPreferences sharedpreferences;

    public void saveAccessToken(Context c, AccessToken response) {
        sharedpreferences = c.getSharedPreferences(APPS_CORE.AUTH_SESSIONS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(APPS_CORE.AUTH_DATA, new Gson().toJson(response));
        editor.commit();
    }

    /**
     * Get Token data
     *
     * @param c
     * @return
     */
    public AccessToken getTokenData(Context c) {
        sharedpreferences = c.getSharedPreferences(APPS_CORE.AUTH_SESSIONS, Context.MODE_PRIVATE);
        String tokenData = sharedpreferences.getString(APPS_CORE.AUTH_DATA, null);
        if (tokenData != null) {
            return new Gson().fromJson(tokenData, AccessToken.class);
        } else {
            return null;
        }
    }

}
