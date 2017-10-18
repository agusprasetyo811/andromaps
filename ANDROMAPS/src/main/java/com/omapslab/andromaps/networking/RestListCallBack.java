package com.omapslab.andromaps.networking;

import java.util.List;

/**
 * Rest Call Back
 *
 * @By Agus Prasetyo | omapslab (agusprasetyo811@gmail.com)
 *-------------------------------------------------------------
 */
public interface RestListCallBack<T> {

    void onSuccess(List<T> response);

    void onError(String header, String message);
}
