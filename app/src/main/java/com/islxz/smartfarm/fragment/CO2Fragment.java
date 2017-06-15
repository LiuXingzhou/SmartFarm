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

public class CO2Fragment extends Fragment implements View.OnClickListener {

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String ip;

    private TextView mTextView1;
    private TextView mTextView2;
    private ImageButton mBlowerIB;
    private ImageButton mBuzzerIB;

    private Control mControl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_co2, null);
        mSharedPreferences = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        ip = mSharedPreferences.getString("ip", "");
        bindID(view);
        return view;
    }

    private void bindID(View view) {
        mTextView1 = view.findViewById(R.id.fc_tv_co2);
        mTextView2 = view.findViewById(R.id.fc_tv_min_max);
        mBlowerIB = view.findViewById(R.id.fc_ib_blower);
        mBuzzerIB = view.findViewById(R.id.fc_ib_buzzer);
        mBlowerIB.setOnClickListener(this);
        mBuzzerIB.setOnClickListener(this);
        mControl = new Control();
        mControl.setBlower(0);
        mControl.setRoadlamp(0);
        mControl.setWaterPump(0);
        mControl.setBuzzer(0);
    }

    public void showView(Sensor sensor, Config config, Control control) {
        if (sensor != null) {
            mTextView1.setText("当前CO2浓度：" + sensor.getCo2());
        }
        if (config != null) {
            mTextView2.setText("CO2设定范围：" + config.getMinCo2() + "~~" + config.getMaxCo2());
        }
        if (control != null) {
            mControl = control;
        }
        buttonState();
    }

    private void buttonState() {
        if (mControl.getBlower() == 0)
            mBlowerIB.setBackgroundResource(R.drawable.dakaifengshan);
        else
            mBlowerIB.setBackgroundResource(R.drawable.dakaifengshan2);

        if (mControl.getBuzzer() == 0)
            mBuzzerIB.setBackgroundResource(R.drawable.dakaibaojing);
        else
            mBuzzerIB.setBackgroundResource(R.drawable.dakaibaojing2);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fc_ib_blower:
                Utility.openOrShut("Blower", mControl, ip, getContext());
                break;
            case R.id.fc_ib_buzzer:
                Utility.openOrShut("Buzzer", mControl, ip, getContext());
                break;
        }
    }

}
