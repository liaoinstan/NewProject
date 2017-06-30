package com.ins.newproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.ins.domain.launcher.DomainLauncher;

public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (BuildConfig.DEBUG) {
//            LoadUpActivity.start(this);
            DomainLauncher.getInstance().startDomainActivity(this, LoadUpActivity.class);
        }else {
            LoadUpActivity.start(this);
        }
        finish();
    }
}
