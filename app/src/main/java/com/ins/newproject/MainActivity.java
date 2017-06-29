package com.ins.newproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ins.common.entity.Image;
import com.ins.common.net.NetApi;
import com.ins.newproject.contacts.ui.activity.SortActivity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView text_log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        PermissionsUtil.checkAndRequestPermissions(this);

        //初始化
        NetApi.setBaseUrl("http://192.168.1.165:8080/");

        text_log = (TextView) findViewById(R.id.text_log);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_netgo:
                netGetBanners();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent();
        switch (item.getItemId()) {
            case R.id.action_photo:
                intent.setClass(this, PhotoActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_permission:
                intent.setClass(this, PermissionActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_contact:
                intent.setClass(this, SortActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //请求banner数据
    private void netGetBanners() {
        Map<String, String> map = new HashMap<String, String>() {{
            //无参数
        }};
        NetApi.NI().getBanners(map).enqueue(new BaseCallback<List<Image>>(new TypeToken<List<Image>>() {
        }.getType()) {
            @Override
            public void onSuccess(int status, List<Image> images, String msg) {
                Log.e("liao", msg);
                text_log.setText(msg);
            }

            @Override
            public void onError(int status, String msg) {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
