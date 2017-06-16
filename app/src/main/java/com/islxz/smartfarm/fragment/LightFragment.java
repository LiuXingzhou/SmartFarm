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

public class LightFragment extends Fragment implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String ip;

    private TextView mTextView1;
    private TextView mTextView2;
    private ImageButton mRoadLampIB;
    private ImageButton mBuzzerIB;

    private Control mControl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_light, null);
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
        mTextView1 = view.findViewById(R.id.fl_tv);
        mTextView2 = view.findViewById(R.id.fl_tv_min_max);
        mRoadLampIB = view.findViewById(R.id.fl_ib_road_lamp);
        mBuzzerIB = view.findViewById(R.id.fl_ib_buzzer);
        mRoadLampIB.setOnClickListener(this);
        mBuzzerIB.setOnClickListener(this);
        mControl = new Control();
        mControl.setBlower(0);
        mControl.setRoadlamp(0);
        mControl.setWaterPump(0);
        mControl.setBuzzer(0);
    }

    public void showView(Sensor sensor, Config config, Control control) {
        if (sensor != null) {
            mTextView1.setText("当前光照强度：" + sensor.getLight());
        }
        if (config != null) {
            mTextView2.setText("正常光照强度：" + config.getMinLight() + "~~" + config.getMaxLight());
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

        if (mControl.getBuzzer() == 0)
            mBuzzerIB.setBackgroundResource(R.drawable.dakaibaojing);
        else
            mBuzzerIB.setBackgroundResource(R.drawable.dakaibaojing2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fl_ib_road_lamp:
                Utility.openOrShut("Roadlamp", mControl, ip, getActivity());
                break;
            case R.id.fl_ib_buzzer:
                Utility.openOrShut("Buzzer", mControl, ip, getActivity());
                break;
        }
    }
}
