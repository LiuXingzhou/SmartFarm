package com.islxz.smartfarm.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.islxz.smartfarm.R;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEditText;
    private Button mButton;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindID();
        mSharedPreferences = this.getSharedPreferences("config", MODE_PRIVATE);
        if (!isFirstRun())
            mEditText.setText(mSharedPreferences.getString("ip", ""));
    }


    private void bindID() {
        mEditText = (EditText) findViewById(R.id.main_edit);
        mButton = (Button) findViewById(R.id.main_btn);
        mButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Pattern pattern = Pattern.compile("\\b((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.(" +
                "(?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)" +
                "\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\.((?!\\d\\d\\d)\\d+|1\\d\\d|2[0-4]\\d|25[0-5])\\b");
        String ip = mEditText.getText().toString();
        if (!ip.equals("")) {
            if (pattern.matcher(ip).matches()) {
                mEditor = mSharedPreferences.edit();
                mEditor.putString("ip", ip);
                mEditor.commit();
                Intent intent = new Intent(this, SmartFarmActivity.class);
                startActivity(intent);
                Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                Toast.makeText(this, "IP地址格式错误", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "请输入IP地址", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean isFirstRun() {
        if (mSharedPreferences.getBoolean("isfirstrun", true)) {
            mEditor = mSharedPreferences.edit();
            mEditor.putBoolean("isfirstrun", false);
            mEditor.commit();
            return true;
        } else
            return false;
    }
}
