package com.islxz.smartfarm.util;

import android.content.Context;

import com.google.gson.Gson;
import com.islxz.smartfarm.gson.Config;
import com.islxz.smartfarm.gson.Control;
import com.islxz.smartfarm.gson.Sensor;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class Utility {

    /**
     * 将JSON解析成Sensor类
     */
    public static Sensor handleSensorResponse(String response) {
        return new Gson().fromJson(response, Sensor.class);
    }

    /**
     * 将JSON解析成Config类
     */
    public static Config handleConfigResponse(String response) {
        return new Gson().fromJson(response, Config.class);
    }

    /**
     * 将JSON解析成Control类
     */
    public static Control handleControlResponse(String response) {
        return new Gson().fromJson(response, Control.class);
    }

    private static int temp;

    /**
     * 控制开关
     *
     * @param name
     * @param control
     * @param ip
     * @param context
     */
    public static void openOrShut(String name, Control control, String ip, Context context) {
        switch (name) {
            case "Blower":
                if (control.getBlower() == 0)
                    temp = 1;
                else
                    temp = 0;
                HttpUtil.sendOkHttpRequest(HttpUrl.HTTP_URL + ip + HttpUrl.SET_CONTROL_URL,
                        "{'Blower':" + temp + "}", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                break;
            case "Roadlamp":
                if (control.getRoadlamp() == 0)
                    temp = 1;
                else
                    temp = 0;
                HttpUtil.sendOkHttpRequest(HttpUrl.HTTP_URL + ip + HttpUrl.SET_CONTROL_URL,
                        "{'Roadlamp':" + temp + "}", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                            }
                        });
                break;
            case "WaterPump":
                if (control.getWaterPump() == 0)
                    temp = 1;
                else
                    temp = 0;
                HttpUtil.sendOkHttpRequest(HttpUrl.HTTP_URL + ip + HttpUrl.SET_CONTROL_URL,
                        "{'WaterPump':" + temp + "}", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                break;
            case "Buzzer":
                if (control.getBuzzer() == 0)
                    temp = 1;
                else
                    temp = 0;
                HttpUtil.sendOkHttpRequest(HttpUrl.HTTP_URL + ip + HttpUrl.SET_CONTROL_URL,
                        "{'Buzzer':" + temp + "}", new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {

                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                            }
                        });
                break;
        }
    }

    private static int absolute(int arg0) {
        if (arg0 < 0)
            return -arg0;
        return arg0;
    }

    public static int priorityLevel(int temp, int min, int max) {
        if (temp < min || temp > max) {
            return 3;
        } else {
            if (absolute((max - temp) - (temp - min)) < (max - min) % 2) {
                return 2;
            } else
                return 1;
        }
    }

    private static int p1;
    private static int p2;

    public static int priorityLevel(int temp1, int min1, int max1, int temp2, int min2, int max2) {
        p1 = priorityLevel(temp1, min1, max1);
        p2 = priorityLevel(temp2, min2, max2);
        if (p1 == 3 || p2 == 3) {
            return 3;
        } else {
            if (p1 == 1 && p2 == 1) {
                return 1;
            } else
                return 2;
        }
    }
}
