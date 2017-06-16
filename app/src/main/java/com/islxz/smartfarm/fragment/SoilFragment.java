package com.islxz.smartfarm.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.islxz.smartfarm.R;
import com.islxz.smartfarm.gson.Config;
import com.islxz.smartfarm.gson.Control;
import com.islxz.smartfarm.gson.Sensor;
import com.islxz.smartfarm.util.Utility;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Qingsu on 2017/6/15.
 */

public class SoilFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String ip;

    private TextView mTemTextView1;
    private TextView mHumTextView1;
    private TextView mTemTextView2;
    private TextView mHumTextView2;
    ;
    private ImageButton mRoadLampIB;
    private ImageButton mWaterPumpIB;
    private ImageButton mBuzzerIB;

    private Control mControl;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_soil, null);
        mSharedPreferences = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        ip = mSharedPreferences.getString("ip", "");
        bindID(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utility.startService(getActivity());
    }

    private void bindID(View view) {
        mTemTextView1 = view.findViewById(R.id.fs_tv_tem);
        mHumTextView1 = view.findViewById(R.id.fs_tv_hum);
        mTemTextView2 = view.findViewById(R.id.fs_tv_min_max_tem);
        mHumTextView2 = view.findViewById(R.id.fs_tv_min_max_hum);
        mRoadLampIB = view.findViewById(R.id.fs_ib_road_lamp);
        mWaterPumpIB = view.findViewById(R.id.fs_ib_water_pump);
        mBuzzerIB = view.findViewById(R.id.fs_ib_buzzer);
        mRoadLampIB.setOnClickListener(this);
        mWaterPumpIB.setOnClickListener(this);
        mBuzzerIB.setOnClickListener(this);

        mControl = new Control();
        mControl.setBlower(0);
        mControl.setRoadlamp(0);
        mControl.setWaterPump(0);
        mControl.setBuzzer(0);
    }

    public void showView(Sensor sensor, Config config, Control control) {
        if (sensor != null) {
            mTemTextView1.setText("当前土壤温度：" + sensor.getSoilTemperature() + "℃");
            mHumTextView1.setText("当前土壤湿度：" + sensor.getSoilHumidity());
        }
        if (config != null) {
            mTemTextView2.setText("温度设定范围：" + config.getMinSoilTemperature() + "~" + config
                    .getMaxSoilTemperature() + "℃");
            mHumTextView2.setText("湿度设定范围：" + config.getMinSoilHumidity() + "~" + config
                    .getMaxSoilHumidity());
        }
        if (control != null) {
            mControl = control;
        }
        buttonState();
    }

    private void buttonState() {
        if (mControl.getRoadlamp() == 0)
            mRoadLampIB.setBackgroundResource(R.drawable.dakaiguangzhao);
        else
            mRoadLampIB.setBackgroundResource(R.drawable.dakaiguangzhao2);

        if (mControl.getWaterPump() == 0)
            mWaterPumpIB.setBackgroundResource(R.drawable.dakaishui);
        else
            mWaterPumpIB.setBackgroundResource(R.drawable.dakaishui2);

        if (mControl.getBuzzer() == 0)
            mBuzzerIB.setBackgroundResource(R.drawable.dakaibaojing);
        else
            mBuzzerIB.setBackgroundResource(R.drawable.dakaibaojing2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fs_ib_road_lamp:
                Utility.openOrShut("Roadlamp", mControl, ip, getActivity());
                break;
            case R.id.fs_ib_water_pump:
                Utility.openOrShut("WaterPump", mControl, ip, getActivity());
                break;
            case R.id.fs_ib_buzzer:
                Utility.openOrShut("Buzzer", mControl, ip, getActivity());
                break;
        }
    }
}
