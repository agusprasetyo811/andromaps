package com.omapslab.andromaps.helpers;

import android.annotation.TargetApi;
import android.os.Build;
import android.util.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Created by omapslab on 5/11/17.
 */

public class EncripterHelper {

    /**
     * Encode
     *
     * @param text
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String base64encode(String text) {
        byte[] data = text.getBytes(StandardCharsets.UTF_8);
        return Base64.encodeToString(data, Base64.DEFAULT);
    }

    /**
     * Decode
     *
     * @param base64
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public String base64decode(String base64) {
        byte[] data = Base64.decode(base64, Base64.DEFAULT);
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * MD5
     *
     * @param md5
     * @return
     */
    public String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return "";
    }
}
