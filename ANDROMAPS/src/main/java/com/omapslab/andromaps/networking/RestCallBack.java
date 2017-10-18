package com.omapslab.andromaps.networking;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest Call Back
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public interface RestCallBack<T> {

    void onSuccess(T response);

    void onSuccess(List<T> response);

    void onSuccess(ArrayList<T> response);

    void onError(String header, String message);
}
