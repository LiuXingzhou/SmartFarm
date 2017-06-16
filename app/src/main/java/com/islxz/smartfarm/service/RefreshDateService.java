package com.islxz.smartfarm.service;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.islxz.smartfarm.util.HttpUrl;
import com.islxz.smartfarm.util.HttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class RefreshDateService extends Service {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String ip;

    private String mSensor;
    private String mConfig;
    private String mControl;

    Intent intent = new Intent(HttpUrl.REFRESH_OK);
    Intent intent1 = new Intent(HttpUrl.REFRESH_ERROR);

    static final RequestBody requestBody = new FormBody.Builder().add("username", "admin").build();

    @Nullable
    @Override

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("Service", "onCreate()");
        mSharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        ip = mSharedPreferences.getString("ip", "");
        reSensorDate(HttpUrl.HTTP_URL + ip + HttpUrl.GET_SENSOR_ULR);
        reConfigDate(HttpUrl.HTTP_URL + ip + HttpUrl.GET_CONFIG_URL);
        reControlDate(HttpUrl.HTTP_URL + ip + HttpUrl.GET_CONTROL_URL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("Service", "onStartCommand()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                getSensorDate(HttpUrl.HTTP_URL + ip + HttpUrl.GET_SENSOR_ULR);
                getConfigDate(HttpUrl.HTTP_URL + ip + HttpUrl.GET_CONFIG_URL);
                getControlDate(HttpUrl.HTTP_URL + ip + HttpUrl.GET_CONTROL_URL);
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    private void reSensorDate(final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    getSensorDate(address);
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }).start();
    }

    private void getSensorDate(String address) {
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendBroadcast(intent1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mSensor = response.body().string();
                intent.putExtra("sensor", mSensor);
                sendBroadcast(intent);
            }
        });
    }

    private void reConfigDate(final String address) {
        final SharedPreferences sharedPreferences = mSharedPreferences;
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    getConfigDate(address);
                    try {
                        Thread.sleep(2500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }).start();

    }

    private void getConfigDate(String address) {
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendBroadcast(intent1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mConfig = response.body().string();
                intent.putExtra("config", mConfig);
                sendBroadcast(intent);
            }
        });
    }

    private void reControlDate(final String address) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                do {
                    getControlDate(address);
                    try {
                        Thread.sleep(400);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } while (true);
            }
        }).start();

    }

    private void getControlDate(String address) {
        HttpUtil.sendOkHttpRequest(address, requestBody, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                sendBroadcast(intent1);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mControl = response.body().string();
                intent.putExtra("control", mControl);
                sendBroadcast(intent);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


}
