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

/**
 * Created by Qingsu on 2017/6/14.
 */

public class SettingFragment extends Fragment implements View.OnClickListener {

    private RelativeLayout mSmartControlRL;
    private RelativeLayout mAboutRL;
    private RelativeLayout mExitRL;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting, null);
        bindID(view);
        return view;
    }

    private void bindID(View view) {
        mSmartControlRL = view.findViewById(R.id.fset_rl_smart_control);
        mAboutRL = view.findViewById(R.id.fset_rl_about);
        mExitRL = view.findViewById(R.id.fset_rl_exit);
        mSmartControlRL.setOnClickListener(this);
        mAboutRL.setOnClickListener(this);
        mExitRL.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), SecondActivity.class);
        switch (view.getId()) {
            case R.id.fset_rl_smart_control:
                intent.putExtra("select", 4);
                break;
            case R.id.fset_rl_about:
                intent.putExtra("select", 5);
                break;
            case R.id.fset_rl_exit:
                getActivity().finish();
                break;
        }
        getActivity().startActivity(intent);
    }
}
