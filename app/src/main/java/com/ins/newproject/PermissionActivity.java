package com.ins.newproject;

import android.Manifest;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.ins.common.utils.PermissionsUtil;

public class PermissionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initBase();
        initView();
        initCtrl();
    }

    private void initBase() {
    }

    private void initView() {
    }

    private void initCtrl() {
    }

    private String[] permissions = new String[]{
            Manifest.permission.WRITE_CONTACTS
    };

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_checkpermission:
                break;
            case R.id.btn_shouldshow:
                break;
            case R.id.btn_requsetpermission:
                break;
        }
    }
}
