package com.islxz.smartfarm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.islxz.smartfarm.R;
import com.islxz.smartfarm.fragment.AirFragment;
import com.islxz.smartfarm.fragment.CO2Fragment;
import com.islxz.smartfarm.fragment.LightFragment;
import com.islxz.smartfarm.fragment.SoilFragment;
import com.islxz.smartfarm.gson.Config;
import com.islxz.smartfarm.gson.Control;
import com.islxz.smartfarm.gson.Sensor;
import com.islxz.smartfarm.util.HttpUrl;
import com.islxz.smartfarm.util.Utility;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitleTV;
    private ImageButton mBackIB;
    private FrameLayout mFrameLayout;

    private CO2Fragment mCO2Fragment;
    private LightFragment mLightFragment;
    private SoilFragment mSoilFragment;
    private AirFragment mAirFragment;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    private MyBroadcast mMyBroadcast;

    private int current = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Intent intent = getIntent();
        current = intent.getIntExtra("select", 0);
        mMyBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter(HttpUrl.REFRESH_OK);
        intentFilter.addAction(HttpUrl.REFRESH_ERROR);
        registerReceiver(mMyBroadcast, intentFilter);
        bindID();
        changeFragment();
    }

    private void changeFragment() {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        switch (current) {
            case 0:
                mTitleTV.setText("CO2详情");
                if (mCO2Fragment == null)
                    mCO2Fragment = new CO2Fragment();
                mFragmentTransaction.replace(R.id.as_fl, mCO2Fragment);
                break;
            case 1:
                mTitleTV.setText("光照详情");
                if (mLightFragment == null)
                    mLightFragment = new LightFragment();
                mFragmentTransaction.replace(R.id.as_fl, mLightFragment);
                break;
            case 2:
                mTitleTV.setText("土壤详情");
                if (mSoilFragment == null)
                    mSoilFragment = new SoilFragment();
                mFragmentTransaction.replace(R.id.as_fl, mSoilFragment);
                break;
            case 3:
                mTitleTV.setText("空气详情");
                if (mAirFragment == null)
                    mAirFragment = new AirFragment();
                mFragmentTransaction.replace(R.id.as_fl, mAirFragment);
                break;
        }
        mFragmentTransaction.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyBroadcast != null)
            unregisterReceiver(mMyBroadcast);
    }

    private void bindID() {
        mTitleTV = (TextView) findViewById(R.id.as_tv_title);
        mBackIB = (ImageButton) findViewById(R.id.as_ib_back);
        mBackIB.setOnClickListener(this);
        mFrameLayout = (FrameLayout) findViewById(R.id.as_fl);
    }

    @Override
    public void onClick(View view) {
        finish();
    }

    private class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(HttpUrl.REFRESH_ERROR)) {
//                Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT).show();
            } else if (action.equals(HttpUrl.REFRESH_OK)) {
                //传感器
                String date1 = intent.getStringExtra("sensor");
                String date2 = intent.getStringExtra("config");
                String date3 = intent.getStringExtra("control");
                Sensor sensor = Utility.handleSensorResponse(date1);
                Config config = Utility.handleConfigResponse(date2);
                Control control = Utility.handleControlResponse(date3);
                switch (current) {
                    case 0:
                        if (mCO2Fragment != null)
                            mCO2Fragment.showView(sensor, config, control);
                        break;
                    case 1:
                        if (mLightFragment != null)
                            mLightFragment.showView(sensor, config, control);
                        break;
                    case 2:
                        if (mSoilFragment != null)
                            mSoilFragment.showView(sensor, config, control);
                        break;
                    case 3:
                        if (mAirFragment != null)
                            mAirFragment.showView(sensor, config, control);
                        break;
                }
            }
        }

    }
}