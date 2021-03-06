package com.ins.domain.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ins.domain.R;

public class AdvanceActivity extends AppCompatActivity implements View.OnClickListener {

    public static void start(Activity context) {
        Intent intent = new Intent(context, AdvanceActivity.class);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.domain_right_in, R.anim.domain_left_out);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.domain_left_in, R.anim.domain_right_out);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.domain_activity_advance);
        if (getSupportActionBar() != null) getSupportActionBar().hide();

        initBase();
        initView();
        initData();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
        findViewById(R.id.btn_advance_permission).setOnClickListener(this);
        findViewById(R.id.btn_advance_wifi).setOnClickListener(this);
        findViewById(R.id.btn_advance_deve).setOnClickListener(this);
    }

    private void initData() {
    }

    private void initCtrl() {
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_advance_permission) {
            startAppSettingActivity(this);
        }else if (i == R.id.btn_advance_wifi) {
            startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
        }else if (i == R.id.btn_advance_deve) {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DEVELOPMENT_SETTINGS);
            startActivity(intent);
        }
    }

    //启调设置页面
    private static void startAppSettingActivity(Context context) {
        try {
            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            String packageName = info.applicationInfo.packageName;

            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", packageName, null);
            intent.setData(uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}

