package com.islxz.smartfarm.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.islxz.smartfarm.R;
import com.islxz.smartfarm.activity.SecondActivity;
import com.islxz.smartfarm.gson.Config;
import com.islxz.smartfarm.gson.Sensor;
import com.islxz.smartfarm.util.Utility;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Qingsu on 2017/6/15.
 */

public class SoilControlFragment extends Fragment {

    private TextView mTemTV;
    private TextView mHumTV;
    private ImageView mTemPriIV;
    private ImageView mHumPriIV;
    private EditText mTemMinEdit;
    private EditText mHumMinEdit;
    private EditText mTemMaxEdit;
    private EditText mHumMaxEdit;
    private RelativeLayout mRelativeLayout;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private String ip;


    private Sensor mSensor;
    private Config mConfig;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_control_soil, null);
        mSharedPreferences = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        ip = mSharedPreferences.getString("ip", "");
        bindID(view);
        Utility.startService(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utility.startService(getActivity());
    }

    private void bindID(View view) {
        mTemTV = view.findViewById(R.id.fcs_tv_tem_num);
        mHumTV = view.findViewById(R.id.fcs_tv_hum_num);
        mTemPriIV = view.findViewById(R.id.fcs_iv_tem_p);
        mHumPriIV = view.findViewById(R.id.fcs_iv_hum_p);
        mTemMinEdit = view.findViewById(R.id.fcs_edit_tem_min);
        mHumMinEdit = view.findViewById(R.id.fcs_edit_hum_min);
        mTemMaxEdit = view.findViewById(R.id.fcs_edit_tem_max);
        mHumMaxEdit = view.findViewById(R.id.fcs_edit_hum_max);
        mRelativeLayout = view.findViewById(R.id.fcs_rl_confirm);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTemMinEdit.getText().toString().equals("") || mTemMaxEdit.getText().toString()
                        .equals("") || mHumMinEdit.getText().toString().equals("") || mHumMaxEdit
                        .getText().toString().equals("") || Integer.parseInt(mTemMinEdit.getText()
                        .toString()) >= Integer.parseInt(mTemMaxEdit.getText().toString()) || Integer
                        .parseInt(mHumMinEdit.getText().toString()) >= Integer.parseInt(mHumMaxEdit
                        .getText().toString())) {
                    Toast.makeText(getContext(), "输入数据错误", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> list = new ArrayList<String>();
                    list.add("soil");
                    list.add(mTemMinEdit.getText().toString());
                    list.add(mTemMaxEdit.getText().toString());
                    list.add(mHumMinEdit.getText().toString());
                    list.add(mHumMaxEdit.getText().toString());
                    list.add(ip);
                    Utility.setConfig(list, getActivity());
                    startActivity(new Intent(getActivity(), SecondActivity.class).putExtra("select", 4));
                    getActivity().finish();
                }
            }
        });
    }

    public void showView(Sensor sensor, Config config) {
        if (sensor != null) {
            mSensor = sensor;
            mTemTV.setText(mSensor.getSoilTemperature() + "℃");
            mHumTV.setText(mSensor.getSoilHumidity() + "");
        }
        if (config != null) {
            if (mConfig == null) {
                mConfig = config;
                mTemMinEdit.setText(mConfig.getMinSoilTemperature() + "");
                mHumMinEdit.setText(mConfig.getMinSoilHumidity() + "");
                mTemMaxEdit.setText(mConfig.getMaxSoilTemperature() + "");
                mHumMaxEdit.setText(mConfig.getMaxSoilHumidity() + "");
            } else
                changeImage(0, Utility.priorityLevel(mSensor.getSoilTemperature(), config
                        .getMinSoilTemperature(), config.getMaxSoilTemperature()));
            changeImage(1, Utility.priorityLevel(mSensor.getSoilHumidity(), config.getMinSoilHumidity(),
                    config.getMaxSoilHumidity()));
        }
    }

    private void changeImage(int iv, int p) {
        switch (iv) {
            case 0:
                switch (p) {
                    case 1:
                        mTemPriIV.setImageResource(R.drawable.p1);
                        break;
                    case 2:
                        mTemPriIV.setImageResource(R.drawable.p2);
                        break;
                    case 3:
                        mTemPriIV.setImageResource(R.drawable.p3);
                        break;
                }
                break;
            case 1:
                switch (p) {
                    case 1:
                        mHumPriIV.setImageResource(R.drawable.p1);
                        break;
                    case 2:
                        mHumPriIV.setImageResource(R.drawable.p2);
                        break;
                    case 3:
                        mHumPriIV.setImageResource(R.drawable.p3);
                        break;
                }
                break;
        }
    }
}
