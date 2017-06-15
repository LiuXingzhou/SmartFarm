package com.islxz.smartfarm.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.islxz.smartfarm.R;
import com.islxz.smartfarm.fragment.HelpFragment;
import com.islxz.smartfarm.fragment.HomeFragment;
import com.islxz.smartfarm.fragment.SettingFragment;
import com.islxz.smartfarm.gson.Config;
import com.islxz.smartfarm.gson.Sensor;
import com.islxz.smartfarm.service.RefreshDateService;
import com.islxz.smartfarm.util.HttpUrl;
import com.islxz.smartfarm.util.Utility;

import java.util.ArrayList;
import java.util.List;

public class SmartFarmActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTitleTV;
    private LinearLayout mHomeLL;
    private LinearLayout mSettingLL;
    private LinearLayout mHelpLL;

    private ImageView mImageView;
    private ImageView mImageView1;
    private ImageView mImageView2;

    private HomeFragment mHomeFragment;
    private SettingFragment mSettingFragment;
    private HelpFragment mHelpFragment;

    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private MyFragmentPagerAdapter mMyFragmentPagerAdapter;
    private FragmentManager mFragmentManager;

    private Intent mServiceIntent;
    private MyBroadcast mMyBroadcast;

    private boolean isExit = false;
    private android.os.Handler myHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public void onBackPressed() {
        if (!isExit) {
            isExit = true;
            myHandler.sendEmptyMessageDelayed(0, 2000);
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
        } else
            finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_farm);
        mServiceIntent = new Intent(this, RefreshDateService.class);
        startService(mServiceIntent);
        mMyBroadcast = new MyBroadcast();
        IntentFilter intentFilter = new IntentFilter(HttpUrl.REFRESH_OK);
        intentFilter.addAction(HttpUrl.REFRESH_ERROR);
        registerReceiver(mMyBroadcast, intentFilter);
        bindID();
        mFragmentList = new ArrayList<>();
        mHomeFragment = new HomeFragment();
        mSettingFragment = new SettingFragment();
        mHelpFragment = new HelpFragment();
        mFragmentList.add(mHomeFragment);
        mFragmentList.add(mSettingFragment);
        mFragmentList.add(mHelpFragment);
        mFragmentManager = getSupportFragmentManager();
        mMyFragmentPagerAdapter = new MyFragmentPagerAdapter(mFragmentManager);
        mViewPager.setAdapter(mMyFragmentPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        changeFragment(0);
                        break;
                    case 1:
                        changeFragment(1);
                        break;
                    case 2:
                        changeFragment(2);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMyBroadcast != null)
            unregisterReceiver(mMyBroadcast);
        if (mServiceIntent != null)
            stopService(mServiceIntent);
    }

    private void bindID() {
        mTitleTV = (TextView) findViewById(R.id.asf_tv_title);
        mHomeLL = (LinearLayout) findViewById(R.id.asf_ll_home);
        mHomeLL.setOnClickListener(this);
        mSettingLL = (LinearLayout) findViewById(R.id.asf_ll_setting);
        mSettingLL.setOnClickListener(this);
        mHelpLL = (LinearLayout) findViewById(R.id.asf_ll_help);
        mHelpLL.setOnClickListener(this);
        mImageView = (ImageView) findViewById(R.id.asf_iv_home);
        mImageView1 = (ImageView) findViewById(R.id.asf_iv_setting);
        mImageView2 = (ImageView) findViewById(R.id.asf_iv_help);
        mViewPager = (ViewPager) findViewById(R.id.asf_vp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.asf_ll_home:
                changeFragment(0);
                break;
            case R.id.asf_ll_setting:
                changeFragment(1);
                break;
            case R.id.asf_ll_help:
                changeFragment(2);
                break;
        }
    }

    public void changeFragment(int arg0) {
        mImageView.setImageResource(R.drawable.b1);
        mImageView1.setImageResource(R.drawable.b2);
        mImageView2.setImageResource(R.drawable.b3);
        switch (arg0) {
            case 0:
                mImageView.setImageResource(R.drawable.shouye_lu);
                mTitleTV.setText("智能农业");
                mViewPager.setCurrentItem(0);
                break;
            case 1:
                mImageView1.setImageResource(R.drawable.shezhi_lu);
                mTitleTV.setText("设置");
                mViewPager.setCurrentItem(1);
                break;
            case 2:
                mImageView2.setImageResource(R.drawable.bangzhu_lu);
                mTitleTV.setText("帮助");
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     * FramgentPager适配器
     */
    class MyFragmentPagerAdapter extends FragmentPagerAdapter {

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }
    }

    private class MyBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(HttpUrl.REFRESH_ERROR)) {
//                Toast.makeText(context, "数据加载失败", Toast.LENGTH_SHORT).show();
            } else if (action.equals(HttpUrl.REFRESH_OK)) {
                Sensor sensor = Utility.handleSensorResponse(intent.getStringExtra("sensor"));
                Config config = Utility.handleConfigResponse(intent.getStringExtra("config"));
                if (mHomeFragment != null)
                    mHomeFragment.initView(sensor, config);
            }
        }
    }


}
