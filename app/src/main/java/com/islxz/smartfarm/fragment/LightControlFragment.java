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

public class LightControlFragment extends Fragment {

    private TextView mLightTV;
    private ImageView mPriIV;
    private EditText mMinEdit;
    private EditText mMaxEdit;
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
        View view = inflater.inflate(R.layout.fragment_control_light, null);
        mSharedPreferences = getActivity().getSharedPreferences("config", MODE_PRIVATE);
        ip = mSharedPreferences.getString("ip", "");
        bindID(view);
        return view;
    }

    private void bindID(View view) {
        mLightTV = view.findViewById(R.id.fcl_tv_num);
        mPriIV = view.findViewById(R.id.fcl_iv_p);
        mMinEdit = view.findViewById(R.id.fcl_edit_min);
        mMaxEdit = view.findViewById(R.id.fcl_edit_max);
        mRelativeLayout = view.findViewById(R.id.fcl_rl_confirm);
        mRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Integer.parseInt(mMinEdit.getText().toString()) >= Integer.parseInt(mMaxEdit
                        .getText()
                        .toString())) {
                    Toast.makeText(getContext(), "输入数据错误", Toast.LENGTH_SHORT).show();
                } else {
                    List<String> list = new ArrayList<String>();
                    list.add("light");
                    list.add(mMinEdit.getText().toString());
                    list.add(mMaxEdit.getText().toString());
                    list.add(ip);
                    Utility.setConfig(list, getContext());
                    startActivity(new Intent(getActivity(), SecondActivity.class).putExtra("select", 4));
                    getActivity().finish();
                }
            }
        });

    }

    public void showView(Sensor sensor, Config config) {
        if (sensor != null) {
            mSensor = sensor;
            mLightTV.setText(mSensor.getLight() + "");
        }
        if (config != null) {
            if (mConfig == null) {
                mConfig = config;
                mMinEdit.setText(mConfig.getMinLight() + "");
                mMaxEdit.setText(mConfig.getMaxLight() + "");
            } else
                changeImage(Utility.priorityLevel(mSensor.getLight(), config.getMinLight(), config
                        .getMaxLight()));
        }
    }

    private void changeImage(int p) {
        switch (p) {
            case 1:
                mPriIV.setImageResource(R.drawable.p1);
                break;
            case 2:
                mPriIV.setImageResource(R.drawable.p2);
                break;
            case 3:
                mPriIV.setImageResource(R.drawable.p3);
                break;
        }
    }

}
