package com.islxz.smartfarm.gson;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class Sensor {

    /**
     * result : ok
     * airHumidity : 50
     * PM25 : -1
     * airTemperature : 30
     * soilTemperature : 32
     * co2 : 77
     * soilHumidity : 51
     * light : 1794
     */

    private String result;
    private int airHumidity;
    private int PM25;
    private int airTemperature;
    private int soilTemperature;
    private int co2;
    private int soilHumidity;
    private int light;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getAirHumidity() {
        return airHumidity;
    }

    public void setAirHumidity(int airHumidity) {
        this.airHumidity = airHumidity;
    }

    public int getPM25() {
        return PM25;
    }

    public void setPM25(int PM25) {
        this.PM25 = PM25;
    }

    public int getAirTemperature() {
        return airTemperature;
    }

    public void setAirTemperature(int airTemperature) {
        this.airTemperature = airTemperature;
    }

    public int getSoilTemperature() {
        return soilTemperature;
    }

    public void setSoilTemperature(int soilTemperature) {
        this.soilTemperature = soilTemperature;
    }

    public int getCo2() {
        return co2;
    }

    public void setCo2(int co2) {
        this.co2 = co2;
    }

    public int getSoilHumidity() {
        return soilHumidity;
    }

    public void setSoilHumidity(int soilHumidity) {
        this.soilHumidity = soilHumidity;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }
}
