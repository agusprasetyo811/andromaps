package com.omapslab.andromaps.networking;

import java.util.ArrayList;
import java.util.List;

/**
 * Rest Call Back
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public interface RestArrayListCallBack<T> {

    void onSuccess(ArrayList<T> response);

    void onError(String header, String message);
}
