package com.omapslab.andromaps.networking;

/**
 * Rest Call Back
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public interface RestCallBack<T> {

    void onSuccess(T response);

    void onError(String header, String message);
}
