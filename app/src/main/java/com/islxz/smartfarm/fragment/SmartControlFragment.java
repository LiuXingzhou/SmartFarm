package com.islxz.smartfarm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.islxz.smartfarm.R;
import com.islxz.smartfarm.activity.SecondActivity;
import com.islxz.smartfarm.util.Utility;

/**
 * Created by Qingsu on 2017/6/15.
 */

public class SmartControlFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mCO2SettingRL;
    private RelativeLayout mLightSettingRL;
    private RelativeLayout mAirSettingRL;
    private RelativeLayout mSoilSettingRL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_smart_control, null);
        bindID(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utility.startService(getActivity());
    }

    private void bindID(View view) {
        mCO2SettingRL = view.findViewById(R.id.fsc_rl_co2);
        mLightSettingRL = view.findViewById(R.id.fsc_rl_light);
        mAirSettingRL = view.findViewById(R.id.fsc_rl_air);
        mSoilSettingRL = view.findViewById(R.id.fsc_rl_soil);
        mCO2SettingRL.setOnClickListener(this);
        mLightSettingRL.setOnClickListener(this);
        mAirSettingRL.setOnClickListener(this);
        mSoilSettingRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        switch (view.getId()) {
            case R.id.fsc_rl_co2:
                intent.putExtra("select", 6);
                break;
            case R.id.fsc_rl_light:
                intent.putExtra("select", 7);
                break;
            case R.id.fsc_rl_air:
                intent.putExtra("select", 8);
                break;
            case R.id.fsc_rl_soil:
                intent.putExtra("select", 9);
                break;
        }
        getActivity().startActivity(intent);
        getActivity().finish();
    }
}
