package com.islxz.smartfarm.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.islxz.smartfarm.R;
import com.islxz.smartfarm.activity.SecondActivity;
import com.islxz.smartfarm.gson.Config;
import com.islxz.smartfarm.gson.Sensor;
import com.islxz.smartfarm.util.Utility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Qingsu on 2017/6/14.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {

    private TextView mCO2TV1;
    private TextView mCO2TV2;
    private ImageView mCO2PIV;
    private RelativeLayout mCO2RL;

    private TextView mLightTV1;
    private TextView mLightTV2;
    private ImageView mLightPIV;
    private RelativeLayout mLightRL;

    private TextView mSoilTemTV1;
    private TextView mSoilHumTV1;
    private TextView mSoilTemTV2;
    private TextView mSoilHumTV2;
    private ImageView mSoilPIV;
    private RelativeLayout mSoilRL;

    private TextView mAirTemTV1;
    private TextView mAirHumTV1;
    private TextView mAirTemTV2;
    private TextView mAirHumTV2;
    private ImageView mAirPIV;
    private RelativeLayout mAirRL;

    private ViewPager mViewPager;
    private List<View> mPagerViewList;
    private MyPagerAdapter mMyPagerAdapter;

    private Sensor mSensor;
    private Config mConfig;

    private android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (mViewPager.getCurrentItem() == mPagerViewList.size() - 1)
                mViewPager.setCurrentItem(0);
            else
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, null);
        bindID(view);
        initViewPager(inflater);
        startPlay();
        Utility.startService(getActivity());
        return view;
    }

    private void startPlay() {
        new Thread() {
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mHandler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public void initView(Sensor sensor, Config config) {
        if (sensor != null) {
            mSensor = sensor;
            mCO2TV1.setText(sensor.getCo2() + "");
            mLightTV1.setText(sensor.getLight() + "");
            mSoilHumTV1.setText(sensor.getSoilHumidity() + "");
            mSoilTemTV1.setText(sensor.getSoilTemperature() + "℃");
            mAirHumTV1.setText(sensor.getAirHumidity() + "");
            mAirTemTV1.setText(sensor.getAirTemperature() + "℃");
        }
        if (config != null) {
            mConfig = config;
            mCO2TV2.setText(config.getMinCo2() + "-" + config.getMaxCo2());
            mLightTV2.setText(config.getMinLight() + "-" + config.getMaxLight());
            mSoilHumTV2.setText(config.getMinSoilHumidity() + "~" + config.getMaxSoilHumidity());
            mSoilTemTV2.setText(config.getMinSoilTemperature() + "~" + config.getMaxSoilTemperature()
                    + "℃");
            mAirHumTV2.setText(config.getMinAirHumidity() + "~" + config.getMaxAirHumidity());
            mAirTemTV2.setText(config.getMinAirTemperature() + "~" + config.getMaxAirTemperature() +
                    "℃");
        }
        if (mSensor != null && mConfig != null) {
            changeImage(0, Utility.priorityLevel(mSensor.getCo2(), mConfig.getMinCo2(), mConfig
                    .getMaxCo2()));
            changeImage(1, Utility.priorityLevel(mSensor.getLight(), mConfig.getMinLight(), mConfig
                    .getMaxLight()));
            changeImage(2, Utility.priorityLevel(mSensor.getSoilTemperature(), mConfig
                            .getMinSoilTemperature(), mConfig.getMaxCo2(), mSensor.getSoilHumidity(),
                    mConfig.getMinSoilHumidity(),
                    mConfig.getMaxSoilHumidity()));
            changeImage(3, Utility.priorityLevel(mSensor.getAirTemperature(), mConfig
                            .getMinAirTemperature(), mConfig.getMaxAirTemperature(), mSensor
                            .getAirHumidity(),
                    mConfig.getMinAirHumidity(), mConfig.getMaxAirHumidity()));
        }
    }

    private void changeImage(int iv, int p) {
        switch (iv) {
            case 0:
                switch (p) {
                    case 1:
                        mCO2PIV.setImageResource(R.drawable.p1);
                        break;
                    case 2:
                        mCO2PIV.setImageResource(R.drawable.p2);
                        break;
                    case 3:
                        mCO2PIV.setImageResource(R.drawable.p3);
                        break;
                }
                break;
            case 1:
                switch (p) {
                    case 1:
                        mLightPIV.setImageResource(R.drawable.p1);
                        break;
                    case 2:
                        mLightPIV.setImageResource(R.drawable.p2);
                        break;
                    case 3:
                        mLightPIV.setImageResource(R.drawable.p3);
                        break;
                }
                break;
            case 2:
                switch (p) {
                    case 1:
                        mSoilPIV.setImageResource(R.drawable.p1);
                        break;
                    case 2:
                        mSoilPIV.setImageResource(R.drawable.p2);
                        break;
                    case 3:
                        mSoilPIV.setImageResource(R.drawable.p3);
                        break;
                }
                break;
            case 3:
                switch (p) {
                    case 1:
                        mAirPIV.setImageResource(R.drawable.p1);
                        break;
                    case 2:
                        mAirPIV.setImageResource(R.drawable.p2);
                        break;
                    case 3:
                        mAirPIV.setImageResource(R.drawable.p3);
                        break;
                }
                break;
        }
    }

    private void initViewPager(LayoutInflater inflater) {
        mPagerViewList = new ArrayList<>();
        View v1 = inflater.inflate(R.layout.item_pager, null);
        ImageView iv1 = v1.findViewById(R.id.ip_iv);
        iv1.setImageResource(R.drawable.bana1);
        View v2 = inflater.inflate(R.layout.item_pager, null);
        ImageView iv2 = v2.findViewById(R.id.ip_iv);
        iv2.setImageResource(R.drawable.bana2);
        View v3 = inflater.inflate(R.layout.item_pager, null);
        ImageView iv3 = v3.findViewById(R.id.ip_iv);
        iv3.setImageResource(R.drawable.bana3);
        mPagerViewList.add(v1);
        mPagerViewList.add(v2);
        mPagerViewList.add(v3);
        mMyPagerAdapter = new MyPagerAdapter();
        mViewPager.setAdapter(mMyPagerAdapter);
    }

    private void bindID(View view) {
        mViewPager = view.findViewById(R.id.fh_vp);

        mCO2TV1 = view.findViewById(R.id.ihc_tv_co2_num);
        mCO2TV2 = view.findViewById(R.id.ihc_tv_co2_min_max);
        mCO2PIV = view.findViewById(R.id.ihc_iv_p_co2);
        mCO2RL = view.findViewById(R.id.fh_rl_co2);

        mLightTV1 = view.findViewById(R.id.ihc_tv_light_num);
        mLightTV2 = view.findViewById(R.id.ihc_tv_light_min_max);
        mLightPIV = view.findViewById(R.id.ihc_iv_p_light);
        mLightRL = view.findViewById(R.id.fh_rl_light);

        mSoilTemTV1 = view.findViewById(R.id.ihc_tv_soiltem_num);
        mSoilHumTV1 = view.findViewById(R.id.ihc_tv_soilhum_num);
        mSoilTemTV2 = view.findViewById(R.id.ihc_tv_soiltem_min_max);
        mSoilHumTV2 = view.findViewById(R.id.ihc_tv_soilhum_min_max);
        mSoilPIV = view.findViewById(R.id.ihc_iv_p_soil);
        mSoilRL = view.findViewById(R.id.fh_rl_soil);

        mAirTemTV1 = view.findViewById(R.id.ihc_tv_airtem_num);
        mAirHumTV1 = view.findViewById(R.id.ihc_tv_airhum_num);
        mAirTemTV2 = view.findViewById(R.id.ihc_tv_airtem_min_max);
        mAirHumTV2 = view.findViewById(R.id.ihc_tv_airhum_min_max);
        mAirPIV = view.findViewById(R.id.ihc_iv_p_air);
        mAirRL = view.findViewById(R.id.fh_rl_air);

        mCO2RL.setOnClickListener(this);
        mLightRL.setOnClickListener(this);
        mSoilRL.setOnClickListener(this);
        mAirRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        switch (view.getId()) {
            case R.id.fh_rl_co2:
                intent.putExtra("select", 0);
                break;
            case R.id.fh_rl_light:
                intent.putExtra("select", 1);
                break;
            case R.id.fh_rl_soil:
                intent.putExtra("select", 2);
                break;
            case R.id.fh_rl_air:
                intent.putExtra("select", 3);
                break;
        }
        getActivity().startActivity(intent);
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mPagerViewList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mPagerViewList.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mPagerViewList.get(position));
            return mPagerViewList.get(position);
        }
    }

}
