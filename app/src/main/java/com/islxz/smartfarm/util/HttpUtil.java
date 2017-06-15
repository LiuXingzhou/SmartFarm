package com.islxz.smartfarm.util;

import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class HttpUtil {

    private static final MediaType mMediaType = MediaType.parse("application/json;charset=utf-8");

    public static void sendOkHttpRequest(String address, RequestBody requestBody, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS).readTimeout(3, TimeUnit.SECONDS).build();
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendOkHttpRequest(String address, String string, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.SECONDS).readTimeout(3, TimeUnit.SECONDS).build();
        RequestBody requestBody = RequestBody.create(mMediaType, string);
        Request request = new Request.Builder().url(address).post(requestBody).build();
        client.newCall(request).enqueue(callback);
    }

}
