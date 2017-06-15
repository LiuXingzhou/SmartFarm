package com.islxz.smartfarm.gson;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class Control {
    /**
     * Buzzer : 0
     * result : ok
     * WaterPump : 0
     * Roadlamp : 0
     * Blower : 0
     */

    private int Buzzer;
    private int WaterPump;
    private int Roadlamp;
    private int Blower;
    private String result;

    public int getBuzzer() {
        return Buzzer;
    }

    public void setBuzzer(int Buzzer) {
        this.Buzzer = Buzzer;
    }

    public int getWaterPump() {
        return WaterPump;
    }

    public void setWaterPump(int WaterPump) {
        this.WaterPump = WaterPump;
    }

    public int getRoadlamp() {
        return Roadlamp;
    }

    public void setRoadlamp(int Roadlamp) {
        this.Roadlamp = Roadlamp;
    }

    public int getBlower() {
        return Blower;
    }

    public void setBlower(int Blower) {
        this.Blower = Blower;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
