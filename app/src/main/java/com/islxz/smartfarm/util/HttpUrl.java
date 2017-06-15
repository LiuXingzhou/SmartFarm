package com.islxz.smartfarm.util;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class HttpUrl {

    public static final String REFRESH_OK = "com.islxz.REFRESH_OK";
    public static final String REFRESH_ERROR = "com.islxz.REFRESH_ERROR";

    public static final String HTTP_URL = "http://";

    /**
     * 获取传感器的值
     */
    public static final String GET_SENSOR_ULR = ":8890/type/jason/action/getSensor";

    /**
     * 获取传感器的告警值范围(最大值和最小值)
     */
    public static final String GET_CONFIG_URL = ":8890/type/jason/action/getConfig";

    /**
     * 设置传感器的告警值范围(最大值和最小值)
     * {'maxCo2':17,'maxLight':18,'minCo2':19,'minLight':20,'maxSoilHumidity':21,'minSoilHumidity':22,
     * 'minAirHumidity':23,'minAirTemperature':24,'maxAirHumidity':25,'maxAirTemperature':26,
     * 'controlAuto':1,'maxSoilTemperature':27,'minSoilTemperature':28}
     */
    public static final String SET_CONFIG_URL = ":8890/type/jason/action/setConfig";

    /**
     * 控制器协议
     * {'Buzzer':1}或者{'Roadlamp':1}或者{'Blower':1}或者{'WaterPump':1}
     * 或者
     * {'Buzzer':1,'WaterPump':0,'Blower':1,'Roadlamp':1"}
     */
    public static final String SET_CONTROL_URL = ":8890/type/jason/action/control";

    /**
     * 获取控制器状态协议
     */
    public static final String GET_CONTROL_URL = ":8890/type/jason/action/getContorllerStatus";

}
